package com.example.loftmoney

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loftmoney.web.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_balance.*


class BalanceFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private var expense: Int = 0
    private var income: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBalance()
    }

    override fun onResume() {
        super.onResume()
        getBalance()
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun getBalance() {
        val sharedPrefs = activity?.getSharedPreferences(
                                            getString(R.string.appl_name), Context.MODE_PRIVATE)
        val authToken = sharedPrefs?.getString(AUTH_TOKEN_KEY, "no-token")!!
        val getResponseFromApi = ApiService.createApiService.getBalance(authToken)

        disposable.add(getResponseFromApi
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.status == "success") {
                    expense = it.totalExpense.toInt()
                    income = it.totalIncome.toInt()

                    calculateView(expense, income)
                    setFields(expense, income)
                }
            },
                {
                    it.localizedMessage
                })
        )
    }

    private fun setFields(expense: Int, income: Int) {
        val balanceText = "${(income - expense)}₽"
        val expenseText = "${expense}₽"
        val incomeText = "${income}₽"
        total_balance.text = balanceText
        total_expense.text = expenseText
        total_income.text = incomeText
    }

    private fun calculateView(expense: Int, income: Int) {
        var expenseRate = 0
        var incomeRate = 0

        val total = expense + income
        if (total != 0) {
            expenseRate = 1000 * expense / total
            incomeRate = 1000 * income / total
        }
        diagram_view.setRates(expenseRate, incomeRate)
    }
}