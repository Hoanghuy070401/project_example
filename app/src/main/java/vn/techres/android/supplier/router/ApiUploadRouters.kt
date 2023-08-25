package vn.techres.android.supplier.router

import vn.techres.android.supplier.cache.ConfigCache

@Suppress("FunctionName")
object
ApiUploadRouters {
    private const val VERSION = "v1"
    var UPLOAD_URL_PATH = "${ConfigCache.getConfig().apiUpload}/api/$VERSION/media/upload"

    fun API_GENERATE_FILE(): String {
        return "api/$VERSION/media/generate"
    }
    fun API_UPLOAD_FILE(): String {
        return "${ConfigCache.getConfig().apiUpload}/api/$VERSION/media/upload"
    }

}