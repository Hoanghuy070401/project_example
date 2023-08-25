package vn.techres.android.supplier.http.api

import com.hjq.http.annotation.HttpRename
import vn.techres.android.supplier.BuildConfig
import vn.techres.android.supplier.router.ApiApplicationRouters

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/10/2023
 */
class GetDistrictsApi : BaseApi() {


    @HttpRename("city_id")
    var cityId = 0L

    override fun getApi(): String {
        return ApiApplicationRouters.API_GET_DISTRICTS()
    }


    companion object {
        fun params(cityId: Long): BaseApi {
            val data = GetDistrictsApi()
            data.cityId = cityId
            data.projectID = 8094
            data.authorization= "Bearer 0976b9ad-6e74-4fd7-b512-ccf7a148a277"
            return data
        }
    }
}