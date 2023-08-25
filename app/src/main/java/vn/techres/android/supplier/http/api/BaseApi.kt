package vn.techres.android.supplier.http.api

import com.hjq.http.annotation.HttpHeader
import com.hjq.http.annotation.HttpRename
import com.hjq.http.config.IRequestApi
import com.hjq.http.config.IRequestType
import com.hjq.http.model.BodyType
import vn.techres.android.supplier.BuildConfig
import vn.techres.android.supplier.cache.UserCache
import vn.techres.android.supplier.constants.AppConstants


/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
open class BaseApi : IRequestApi, IRequestType {

    override fun getBodyType(): BodyType {
        return BodyType.JSON
    }

    @HttpHeader
    @HttpRename("ProjectId")
    var projectID = BuildConfig.PROJECT_ID_OAUTH

    @HttpHeader
    @HttpRename("Method")
    var method = AppConstants.HTTP_METHOD_GET

    @HttpHeader
    @HttpRename("Authorization")
    var authorization: String = UserCache.getAccessToken()

    override fun getApi(): String {
        return ""
    }
}