package com.example.loftmoney

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.loftmoney.web.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


const val FRAGMENT_KEY = "fragment"

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        view_pager.adapter = BudgetPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)?.setText(R.string.expenses)
        tabs.getTabAt(1)?.setText(R.string.incomes)
        tabs.getTabAt(2)?.setText(R.string.balance)

        view_pager.addOnPageChangeListener(this)

    /**
         *  Click Add New Item (Floating Action Button)
         */
        btn_fab_main.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)

            when (view_pager.currentItem) {
                0 -> intent.putExtra(EXTRA_KEY, ADD_EXPENSE_ITEM)
                1 -> intent.putExtra(EXTRA_KEY, ADD_INCOME_ITEM)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onPageSelected(position: Int) {
        if (view_pager.currentItem == 2) {
            btn_fab_main.visibility = GONE
        }
        else {
            btn_fab_main.visibility = VISIBLE
        }
    }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_item_logout) {
            doLogout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
        tabs.setBackgroundColor(resources.getColor(R.color.colorApp))
        toolbar.setBackgroundColor(resources.getColor(R.color.colorApp))
        btn_fab_main.visibility = VISIBLE
    }

    override fun onActionModeStarted(mode: ActionMode?) {
        super.onActionModeStarted(mode)
        tabs.setBackgroundColor(resources.getColor(R.color.action_mode_color))
        toolbar.setBackgroundColor(resources.getColor(R.color.action_mode_color))
        btn_fab_main.visibility = View.INVISIBLE
    }

    private fun doLogout() {
        val logoutRequest = ApiService.createApiService.logout()

        disposable.add(logoutRequest
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.status == "success") {
                    getSharedPreferences(AUTH_TOKEN_KEY, Context.MODE_PRIVATE)
                        .edit().clear().apply()
                    startActivity(Intent(this, AuthActivity::class.java))
                }
            },
                {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            )
        )
    }


    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    class BudgetPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val fragment: Fragment

            if (position == 2) {
                fragment = BalanceFragment()
            }
            else {
                fragment = BudgetFragment()
                fragment.arguments = Bundle().apply {
                    putInt(FRAGMENT_KEY, position)
                }
            }
            return fragment
        }

        override fun getCount() = 3
    }
}
