package com.example.loftmoney

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
