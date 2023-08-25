package vn.techres.android.supplier.app.helper

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import vn.techres.android.supplier.utils.AppUtils

class MyAxisValueFormatter : IAxisValueFormatter {
    var s: String? = null

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return AppUtils.getDecimalFormattedString(value.toLong().toString())
    }


}