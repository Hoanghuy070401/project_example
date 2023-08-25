package vn.techres.android.supplier.other

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
object AppConfig {

    /**
     * 当前是否为调试模式
     */
    fun isDebug(): Boolean {
        return vn.techres.android.supplier.BuildConfig.DEBUG
    }

    /**
     * 获取当前构建的模式
     */
    fun getBuildType(): String {
        return vn.techres.android.supplier.BuildConfig.BUILD_TYPE
    }

    /**
     * 当前是否要开启日志打印功能
     */
    fun isLogEnable(): Boolean {
        return vn.techres.android.supplier.BuildConfig.LOG_ENABLE
    }

    /**
     * 获取当前应用的包名
     */
    fun getPackageName(): String {
        return vn.techres.android.supplier.BuildConfig.APPLICATION_ID
    }

    /**
     * 获取当前应用的版本名
     */
    fun getVersionName(): String {
        return vn.techres.android.supplier.BuildConfig.VERSION_NAME
    }

    /**
     * 获取当前应用的版本码
     */
    fun getVersionCode(): Int {
        return vn.techres.android.supplier.BuildConfig.VERSION_CODE
    }

    /**
     * 获取服务器主机地址
     */
    fun getHostUrl(): String {
        return vn.techres.android.supplier.BuildConfig.HOST_URL
    }
}