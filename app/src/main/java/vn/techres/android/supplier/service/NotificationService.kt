package vn.techres.android.supplier.service

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import timber.log.Timber
import vn.techres.android.supplier.constants.AppConstants

class NotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val mmkv: MMKV = MMKV.mmkvWithID("push_token")
        mmkv.putString(AppConstants.PUSH_TOKEN, token).commit()
        Timber.d("push_token_Data %s", token)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.d("Notification data %s", Gson().toJson(message.data))
        Timber.d("Notification bundle %s", Gson().toJson(message))
    }
}
