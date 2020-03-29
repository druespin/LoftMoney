package com.example.loftmoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = BudgetPagerdapter(supportFragmentManager)
        tabs.setupWithViewPager(view_pager)
    }

    class BudgetPagerdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val tabTitles = arrayOf("EXPENSES", "INCOMES")

        override fun getItem(position: Int) = BudgetFragment()

        override fun getCount() = 2

        override fun getPageTitle(position: Int) = tabTitles[position]
    }
}
