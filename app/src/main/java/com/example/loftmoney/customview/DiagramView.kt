package com.example.loftmoney.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.loftmoney.R


class DiagramView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var expenseWidth = 0
    private var incomeWidth = 0

    private val expensePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.expense_text_color)
    }
    private val incomePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.income_text_color)
    }


    private lateinit var expenseRect: Rect
    private lateinit var incomeRect: Rect


    fun setRates(expense: Int, income: Int) {
        expenseWidth = expense
        incomeWidth = income
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)

        expenseRect = Rect(0,0, expenseWidth,150)
        incomeRect = Rect(0,200, incomeWidth,350)

        canvas?.drawRect(expenseRect, expensePaint)
        canvas?.drawRect(incomeRect, incomePaint)
    }
}