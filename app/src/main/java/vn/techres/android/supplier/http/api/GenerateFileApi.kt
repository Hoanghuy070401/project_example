package vn.techres.android.supplier.http.api

import com.hjq.http.annotation.HttpRename
import vn.techres.android.supplier.BuildConfig
import vn.techres.android.supplier.cache.UserCache
import vn.techres.android.supplier.constants.AppConstants
import vn.techres.android.supplier.model.entity.Media
import vn.techres.android.supplier.router.ApiUploadRouters

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/10/2023
 */
class GenerateFileApi : BaseApi() {

    @HttpRename("medias")
    var medias = ArrayList<Media>()

    override fun getApi(): String {
        return ApiUploadRouters.API_GENERATE_FILE()
    }

    companion object {
        fun params(medias: ArrayList<Media>): BaseApi {
            val data = GenerateFileApi()
            data.medias = medias
            data.projectID = BuildConfig.PROJECT_ID_UPLOAD
            data.method = AppConstants.HTTP_METHOD_POST
            data.authorization = String.format(
                "%s %s",
                UserCache.getUser().tokenType,
                UserCache.getUser().jwtToken
            )
            return data
        }
    }
}