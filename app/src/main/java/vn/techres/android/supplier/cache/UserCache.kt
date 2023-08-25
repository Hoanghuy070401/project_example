package vn.techres.android.supplier.cache

import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import vn.techres.android.supplier.constants.AppConstants
import vn.techres.android.supplier.model.entity.User

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
object UserCache {

    private var mUserInfo = User()

    private val mmkv: MMKV = MMKV.mmkvWithID("cache_user")

    fun getUser(): User {
        try {
            mUserInfo =
                Gson().fromJson(mmkv.getString(AppConstants.CACHE_USER, ""), User::class.java)
        } catch (e: Exception) {
            e.stackTrace
        }
        return mUserInfo
    }

    fun saveUser(userInfo: User) {
        try {
            mmkv.remove(AppConstants.CACHE_USER)
            mmkv.putString(AppConstants.CACHE_USER, Gson().toJson(userInfo)).commit()
        } catch (e: Exception) {
            e.stackTrace
        }
    }
    fun isLogin(): Boolean {
        val mUserInfo = getUser()
        return mUserInfo.id > 0
    }

    fun getAccessToken(): String {
        val user = getUser()
        return String.format("%s %s", user.tokenType, user.accessToken)
    }



    fun getNodeToken(): String {
        val user = getUser()
        return String.format("%s %s", user.tokenType, user.jwtToken)
    }
}
