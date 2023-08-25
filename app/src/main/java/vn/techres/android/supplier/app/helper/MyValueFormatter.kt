package vn.techres.android.supplier.app.helper

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import vn.techres.android.supplier.utils.AppUtils

open class MyValueFormatter : IValueFormatter {
    override fun getFormattedValue(
        value: Float,
        entry: Entry?,
        dataSetIndex: Int,
        viewPortHandler: ViewPortHandler?
    ): String? {
        return AppUtils.getDecimalFormattedString(value.toLong().toString())
    }
}