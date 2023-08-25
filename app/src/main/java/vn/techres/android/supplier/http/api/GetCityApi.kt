package vn.techres.android.supplier.http.api

import com.hjq.http.annotation.HttpRename
import vn.techres.android.supplier.BuildConfig
import vn.techres.android.supplier.cache.UserCache
import vn.techres.android.supplier.router.ApiApplicationRouters

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/10/2023
 */
class GetCityApi : BaseApi() {


    @HttpRename("country_id")
    var countryId = 0L


    override fun getApi(): String {
        return ApiApplicationRouters.API_GET_CITY()
    }

    companion object {
        fun params(countryId: Long): BaseApi {
            val data = GetCityApi()
            data.countryId = countryId
            data.projectID = 8094
            data.authorization= "Bearer 0976b9ad-6e74-4fd7-b512-ccf7a148a277"
            return data
        }
    }
}