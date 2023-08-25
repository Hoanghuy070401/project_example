package vn.techres.android.supplier.http.api

import vn.techres.android.supplier.BuildConfig
import vn.techres.android.supplier.router.ApiOauthRouters

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/8/2023
 */
class CheckVersionApi : BaseApi() {

    override fun getApi(): String {
        return ApiOauthRouters.API_GET_VERSION()
    }

    companion object {
        fun params(): BaseApi {
            val data = CheckVersionApi()
            data.projectID = BuildConfig.PROJECT_ID_OAUTH
            return data
        }
    }
}