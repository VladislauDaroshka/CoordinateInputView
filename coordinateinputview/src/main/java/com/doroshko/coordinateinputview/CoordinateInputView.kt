package com.doroshko.coordinateinputview

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import java.lang.StringBuilder

class CoordinateInputView(
    context: Context,
    attributes: AttributeSet
) : LinearLayout(context, attributes) {
    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.coordinate_input_view, this, true)
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is EditText)
                view.filters = arrayOf(InputFilterMinMax(0.0, 180.0))
        }
    }

    class InputFilterMinMax(private val min: Double, private val max: Double) : InputFilter {

        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input =
                    (StringBuilder(dest.toString()).insert(dstart, source)).toString().toDouble() //работает
                if (isInRange(min, max, input))
                    return null
            } catch (e: NumberFormatException) {

            }
            return ""
        }

        private fun isInRange(a: Double, b: Double, c: Double): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }

}