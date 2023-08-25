package vn.techres.android.supplier.router

@Suppress("FunctionName")
object ApiApplicationRouters {

    const val VERSION = "v2"

    fun API_GET_WARD(): String {
        return "api/${VERSION}/administrative-units/wards"
    }

    fun API_GET_DISTRICTS(): String {
        return "api/${VERSION}/administrative-units/districts"
    }

    fun API_GET_CITY(): String {
        return "api/${VERSION}/administrative-units/cities"
    }

}