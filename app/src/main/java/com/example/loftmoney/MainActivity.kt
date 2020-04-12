package com.example.loftmoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

const val FRAGMENT_KEY = "fragment"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = BudgetPagerdapter(supportFragmentManager)
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)!!.setText(R.string.expenses)
        tabs.getTabAt(1)!!.setText(R.string.incomes)


        /**
         *  Click Add New Item
         */
        btn_fab_main.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)

            when (view_pager.currentItem) {
                0 -> intent.putExtra(EXTRA_KEY, ADD_EXPENSE_ITEM)
                1 -> intent.putExtra(EXTRA_KEY, ADD_INCOME_ITEM)
            }
            startActivity(intent)
        }
    }

    class BudgetPagerdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): BudgetFragment {
            val fragment = BudgetFragment()
            fragment.arguments = Bundle().apply {

                putInt(FRAGMENT_KEY, position)
            }
            return fragment
        }

        override fun getCount() = 2
    }
}
