package vn.techres.android.supplier.router

import vn.techres.android.supplier.cache.UserCache

@Suppress("FunctionName")
object ApiRouters {
    private const val VERSION = "v2"

    fun API_FEEDBACK(): String {
        return "api/$VERSION/employees/feedback"
    }




}