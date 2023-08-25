package vn.techres.android.supplier.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import vn.techres.android.supplier.R
import vn.techres.widget.view.RegexEditText

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
class AppEditText : RegexEditText {
    private var typeFont = ResourcesCompat.getFont(context, R.font.roboto_regular)

    constructor(context: Context?) : super(context!!) {
        setFonts()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        setFonts()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        setFonts()
    }

    private fun setFonts() {
        typeface = typeFont
    }

}