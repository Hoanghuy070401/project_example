package vn.techres.android.supplier.app

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.net.ConnectivityManager
import android.net.Network
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.aghajari.emojiview.AXEmojiManager
import com.aghajari.emojiview.facebookprovider.AXFacebookEmojiProvider
import com.hjq.bar.TitleBar
import com.hjq.http.EasyConfig
import com.hjq.http.config.IRequestInterceptor
import com.hjq.http.model.HttpHeaders
import com.hjq.http.model.HttpParams
import com.hjq.http.request.HttpRequest
import com.hjq.toast.ToastUtils
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.mmkv.MMKV
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.data.UploadNotificationAction
import net.gotev.uploadservice.data.UploadNotificationConfig
import net.gotev.uploadservice.data.UploadNotificationStatusConfig
import net.gotev.uploadservice.extensions.flagsCompat
import net.gotev.uploadservice.extensions.getCancelUploadIntent
import net.gotev.uploadservice.observer.request.GlobalRequestObserver
import okhttp3.OkHttpClient
import timber.log.Timber
import vn.techres.android.supplier.BuildConfig
import vn.techres.android.supplier.R
import vn.techres.android.supplier.app.helper.GlobalUploadObserver
import vn.techres.android.supplier.http.model.RequestServer
import vn.techres.android.supplier.other.*
import vn.techres.android.supplier.ui.activity.HomeActivity
import vn.techres.android.supplier.manager.ActivityManager


/**
 * @Author: Bùi Hửu Thắng
 * @Date: 28/09/2022
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initSdk(this)
        createNotificationChannels(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        widthView = Resources.getSystem().displayMetrics.widthPixels
        heightView = Resources.getSystem().displayMetrics.heightPixels
    }

    companion object {
        lateinit var kv: MMKV
        var instance: AppApplication? = null
        const val UPLOAD_CHANNEL_ID = "UPLOAD_CHANNEL_ID" // Kênh thông báo upload
        const val EMPLOYEE_NOTIFICATION_CHANNEL_ID =
            "EMPLOYEE_NOTIFICATION_CHANNEL_ID" // Kênh thông báo nhân viên
        const val SYSTEM_NOTIFICATION_CHANNEL_ID =
            "SYSTEM_NOTIFICATION_CHANNEL_ID" // Kênh thông báo hệ thống

        lateinit var channelUpload: NotificationChannel
        lateinit var channelEmployee: NotificationChannel
        lateinit var channelSystem: NotificationChannel
        lateinit var notificationManager: NotificationManager

        var widthView = 0
        var heightView = 0

        fun initSdk(application: Application) {
            // Đặt trình khởi tạo thanh tiêu đề
            TitleBar.setDefaultStyle(TitleBarStyle())

            SmartRefreshLayout.setDefaultRefreshHeaderCreator { _: Context?, _: RefreshLayout? ->
                MaterialHeader(
                    application
                ).setColorSchemeColors(
                    ContextCompat.getColor(
                        application,
                        R.color.common_accent_color
                    )
                )
            }

            SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
                layout.setEnableAutoLoadMore(false)
                layout.setEnableOverScrollDrag(true)
                layout.setEnableOverScrollBounce(true)
                layout.setEnableLoadMoreWhenContentNotFull(true)
                layout.setEnableScrollContentWhenRefreshed(true)
                layout.setPrimaryColorsId(R.color.main_bg, android.R.color.white)
                layout.setFooterMaxDragRate(4.0f)
                layout.setFooterHeight(0f)
            }

            // Khởi tạo toast
            ToastUtils.init(application, ToastStyle())

            //đặt chế độ gỡ lỗi
            ToastUtils.setDebugMode(AppConfig.isDebug())

            // cài đặt Toast
            ToastUtils.setInterceptor(ToastLogInterceptor())

            // cài đặt Crash
            CrashHandler.register(application)

            //Lưu cache
            MMKV.initialize(application)

            // Activity
            ActivityManager.getInstance().init(application)

            // Khởi tạo khung yêu cầu mạng
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .build()

            //Khởi tạo máy chủ java
            EasyConfig.with(okHttpClient)
                // có in nhật ký không
                .setLogEnabled(AppConfig.isLogEnable())
                //Thiết lập cấu hình máy chủ
                .setServer(RequestServer())
                //Đặt chính sách xử lý yêu cầu
                .setHandler(vn.techres.android.supplier.http.model.RequestHandler(application))
                // Đặt bộ chặn tham số yêu cầu
                .setInterceptor(object : IRequestInterceptor {
                    @SuppressLint("HardwareIds")
                    override fun interceptArguments(
                        httpRequest: HttpRequest<*>,
                        params: HttpParams,
                        headers: HttpHeaders
                    ) {
                        headers.put("time", System.currentTimeMillis().toString())
                        headers.put("Content-Type", "application/json")
                        headers.put("Cache-Control", "no-store")
                        headers.put("Cache-Control", "no-cache")
                        headers.put(
                            "udid", Settings.Secure.getString(
                                application.contentResolver,
                                Settings.Secure.ANDROID_ID
                            )
                        )
                        headers.put("os_name", "android_supplier")
                    }
                })
                // Đặt số lần yêu cầu thử lại
                .setRetryCount(1)
                // Đặt thời gian thử lại yêu cầu
                .setRetryTime(2000)
                .into()

            // Khởi tạo in nhật ký
            if (AppConfig.isLogEnable()) {
                Timber.plant(DebugLoggerTree())
            }

            // Đăng ký theo dõi thay đổi trạng thái mạng
            val connectivityManager: ConnectivityManager? =
                ContextCompat.getSystemService(application, ConnectivityManager::class.java)
            if (connectivityManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object :
                    ConnectivityManager.NetworkCallback() {
                    @SuppressLint("MissingPermission")
                    override fun onLost(network: Network) {
                        val topActivity: Activity? = ActivityManager.getInstance().getTopActivity()
                        if (topActivity !is LifecycleOwner) {
                            return
                        }
                        val lifecycleOwner: LifecycleOwner = topActivity
                        if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) {
                            return
                        }
                        ToastUtils.show(R.string.common_network_error)
                    }
                })
            }

            //Cài đặt upload service
            UploadServiceConfig.initialize(
                context = application,
                defaultNotificationChannel = UPLOAD_CHANNEL_ID,
                debug = BuildConfig.DEBUG
            )

            GlobalRequestObserver(
                application, GlobalUploadObserver()
            )


            //Setup Emoji
            AXEmojiManager.install(application, AXFacebookEmojiProvider(application))
            UI.mEmojiView = true
            UI.mSingleEmojiView = false
            UI.mStickerView = true
            UI.mCustomView = false
            UI.mFooterView = true
            UI.mCustomFooter = false
            UI.mWhiteCategory = true
            UI.loadTheme()

            AXEmojiManager.getEmojiViewTheme().footerSelectedItemColor =
                ContextCompat.getColor(application, R.color.main_bg)
            AXEmojiManager.getEmojiViewTheme().footerBackgroundColor = Color.WHITE
            AXEmojiManager.getEmojiViewTheme().selectionColor = Color.TRANSPARENT
            AXEmojiManager.getEmojiViewTheme().selectedColor =
                ContextCompat.getColor(application, R.color.main_bg)
            AXEmojiManager.getEmojiViewTheme().categoryColor = Color.WHITE
            AXEmojiManager.getEmojiViewTheme().setAlwaysShowDivider(true)
            AXEmojiManager.getEmojiViewTheme().backgroundColor =
                ContextCompat.getColor(application, R.color.white)

            AXEmojiManager.getStickerViewTheme().selectionColor =
                ContextCompat.getColor(application, R.color.main_bg)
            AXEmojiManager.getStickerViewTheme().selectedColor =
                ContextCompat.getColor(application, R.color.main_bg)
            AXEmojiManager.getStickerViewTheme().backgroundColor = Color.WHITE
            AXEmojiManager.getStickerViewTheme().categoryColor =
                ContextCompat.getColor(application, R.color.white)
            AXEmojiManager.getStickerViewTheme().setAlwaysShowDivider(true)
        }

        fun getNotificationConfig(
            context: Context,
            uploadId: String,
            @StringRes title: Int
        ): UploadNotificationConfig {
            val clickIntent = PendingIntent.getActivity(
                context,
                1,
                Intent(context, HomeActivity::class.java),
                flagsCompat(PendingIntent.FLAG_UPDATE_CURRENT)
            )

            val autoClear = true
            val largeIcon: Bitmap? = null
            val clearOnAction = true
            val ringToneEnabled = true

            val cancelAction = UploadNotificationAction(
                R.drawable.ic_ban_notification,
                context.getString(R.string.cancel_upload),
                context.getCancelUploadIntent(uploadId)
            )

            val noActions = ArrayList<UploadNotificationAction>(1)
            val progressActions = ArrayList<UploadNotificationAction>(1)
            progressActions.add(cancelAction)

            val progress = UploadNotificationStatusConfig(
                context.getString(title),
                context.getString(R.string.uploading),
                R.drawable.ic_upload_notification,
                Color.BLUE,
                largeIcon,
                clickIntent,
                progressActions,
                clearOnAction,
                autoClear
            )

            val success = UploadNotificationStatusConfig(
                context.getString(title),
                context.getString(R.string.upload_success),
                R.drawable.ic_success_notification,
                Color.GREEN,
                largeIcon,
                clickIntent,
                noActions,
                clearOnAction,
                autoClear
            )

            val error = UploadNotificationStatusConfig(
                context.getString(title),
                context.getString(R.string.upload_error),
                R.drawable.ic_error_notification,
                Color.RED,
                largeIcon,
                clickIntent,
                noActions,
                clearOnAction,
                autoClear
            )

            val cancelled = UploadNotificationStatusConfig(
                context.getString(title),
                context.getString(R.string.upload_cancelled),
                R.drawable.ic_ban_notification,
                Color.YELLOW,
                largeIcon,
                clickIntent,
                noActions,
                clearOnAction
            )

            return UploadNotificationConfig(
                UPLOAD_CHANNEL_ID, ringToneEnabled, progress, success, error, cancelled
            )
        }
    }

    private fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelUpload = NotificationChannel(
                UPLOAD_CHANNEL_ID,
                "Kênh tiến trình tải",
                NotificationManager.IMPORTANCE_LOW
            )
            channelUpload.description =
                "Kênh thông báo tiến trình tải hình ảnh, video lên ứng dụng quản lý nhà hàng SEEMT."
            channelUpload.setSound(null, null)

            //Kênh thong báo cong viec, quyen loi nhan vien
            channelEmployee = NotificationChannel(
                EMPLOYEE_NOTIFICATION_CHANNEL_ID,
                "Kênh hoạt động, nhân viên",
                NotificationManager.IMPORTANCE_HIGH
            )
            channelEmployee.description =
                "Kênh thông báo các hoạt động và quyền lợi của nhân viên hệ thống nhà hàng."
            channelEmployee.enableLights(true)
            channelEmployee.enableVibration(true)
            channelEmployee.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val alarmSoundEmployee = Uri.parse(
                "android.resource://"
                        + AppConfig.getPackageName() + "/" + R.raw.notification_work_and_employee
            )
            val audioAttributesEmployee = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                .build()
            channelEmployee.setSound(alarmSoundEmployee, audioAttributesEmployee)


            channelSystem = NotificationChannel(
                SYSTEM_NOTIFICATION_CHANNEL_ID, "Kênh hệ thống", NotificationManager.IMPORTANCE_HIGH
            )
            channelSystem.description = "Kênh thông báo hệ thống nhà hàng."
            channelSystem.enableLights(true)
            channelSystem.enableVibration(true)
            channelSystem.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val alarmSoundSystem = Uri.parse(
                "android.resource://"
                        + AppConfig.getPackageName() + "/" + R.raw.notification_other
            )
            val audioAttributesSystem =
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                    .build()
            channelSystem.setSound(alarmSoundSystem, audioAttributesSystem)
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channelUpload)
            notificationManager.createNotificationChannel(channelEmployee)
            notificationManager.createNotificationChannel(channelSystem)
        }
    }
}