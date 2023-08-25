package vn.techres.android.supplier.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.provider.Settings
import android.text.Html
import android.text.InputFilter
import android.text.Spanned
import android.util.Base64
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import timber.log.Timber
import vn.techres.android.supplier.app.AppApplication
import vn.techres.android.supplier.constants.AppConstants
import vn.techres.android.supplier.other.CenterLayoutManager
import vn.techres.android.supplier.other.PreCachingLayoutManager
import vn.techres.android.supplier.widget.AppEditText
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.NetworkInterface
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern


object AppUtils {
    /** Trả về tên thiết bị  */
    fun getDeviceName(): String {
        return Build.MANUFACTURER + " - " + Build.MODEL
    }

    fun getLocalIpAddress(): String? {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (into in interfaces) {
                val addresses = into.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress) {
                        val ip = address.hostAddress
                        if (ip.contains(":")) // Kiểm tra xem địa chỉ IP có chứa dấu hai chấm (:) không (IPv6)
                            continue
                        return ip
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    fun setFileToStorageDir(fileName: String): File {
        return File(
            Environment.getExternalStorageDirectory(),
            Environment.DIRECTORY_DOWNLOADS + File.separator + AppConstants.FOLDER_APP + File.separator + fileName.replace(
                "%20",
                ""
            )
        )
    }

    fun fromHtml(string: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(string)
        }
    }

    fun getDecimalFormattedString(value: BigDecimal): String {
        val formatter = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.US))
        return formatter.format(value)
    }

    fun getMoneyFormatted(value: BigDecimal): String {
        val formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))
        return formatter.format(value)
    }

    fun getDecimalFormattedString(value: String): String {
        var value = value
        if (value.contains("-")) {
            value = value.substring(1)
            val lst = StringTokenizer(value, ".")
            var str1 = value
            var str2 = ""
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken()
                str2 = lst.nextToken()
            }
            var str3 = StringBuilder()
            var i = 0
            var j = -1 + str1.length
            if (str1[-1 + str1.length] == '.') {
                j--
                str3 = StringBuilder(".")
            }
            var k = j
            while (true) {
                if (k < 0) {
                    if (str2.length > 0) {
                        str3.append(".").append(str2)
                    }
                    return String.format("-%s", str3)
                }
                if (i == 3) {
                    str3.insert(0, ",")
                    i = 0
                }
                str3.insert(0, str1[k])
                i++
                k--
            }
        } else {
            val lst = StringTokenizer(value, ".")
            var str1 = value
            var str2 = ""
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken()
                str2 = lst.nextToken()
            }
            var str3 = StringBuilder()
            var i = 0
            var j = -1 + str1.length
            if (str1[-1 + str1.length] == '.') {
                j--
                str3 = StringBuilder(".")
            }
            var k = j
            while (true) {
                if (k < 0) {
                    if (str2.length > 0) {
                        str3.append(".").append(str2)
                    }
                    return str3.toString()
                }
                if (i == 3) {
                    str3.insert(0, ",")
                    i = 0
                }
                str3.insert(0, str1[k])
                i++
                k--
            }
        }
    }

    fun roundDouble(numberF: Double?, roundTo: Int): Double {
        val mF: Double
        val num = java.lang.StringBuilder("#########.")
        for (count in 0 until roundTo) {
            num.append("0")
        }
        val df = DecimalFormat(num.toString())
        df.roundingMode = RoundingMode.HALF_UP
        val mS = df.format(numberF).replace(",", ".")
        mF = mS.toDouble()
        return mF
    }

    fun initRecyclerView(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        configRecyclerView(
            view,
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        )
        view.adapter = adapter
    }

    fun configRecyclerView(
        recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager?
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        (recyclerView.itemAnimator)!!.changeDuration = 0
        ((recyclerView.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations =
            false
        recyclerView.isNestedScrollingEnabled = false
    }

    fun initRecyclerViewVertical(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        configRecyclerView(
            view, PreCachingLayoutManager(
                view.context, RecyclerView.VERTICAL, false
            )
        )
        view.adapter = adapter
    }

    fun initRecyclerViewVertical(
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?,
        count: Int
    ) {
        configRecyclerView(view, GridLayoutManager(view.context, count))
        view.adapter = adapter
    }



    fun initRecyclerViewHorizontal(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        configRecyclerView(
            view, PreCachingLayoutManager(
                view.context, RecyclerView.HORIZONTAL, false
            )
        )
        view.adapter = adapter
    }

    fun initRecyclerViewHorizontal(
        view: RecyclerView,
        adapter: RecyclerView.Adapter<*>?,
        count: Int
    ) {
        configRecyclerView(view, GridLayoutManager(view.context, count))
        view.adapter = adapter
    }

    fun initRecyclerViewReverse(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        val preCachingLayoutManager = CenterLayoutManager(
            view.context,
            RecyclerView.VERTICAL,
            true
        )
        configRecyclerView(view, preCachingLayoutManager)
        view.adapter = adapter
    }

    //format điểm đánh giá nhà hàng
    //ví dụ: 5.0 ---> 5, 5.1, 5.2 ---> 5.1/5.2
    fun formatDoubleToString(value: Double): String {
        val s: String = if (value.toInt().toDouble().compareTo(value) == 0) {
            java.lang.String.format(Locale.getDefault(), "%s", value.toInt())
        } else {
            java.lang.String.format(Locale.getDefault(), "%s", value)
        }
        return s
    }

    //Round double to 2 decimal
    fun roundOffDecimal(numInDouble: Double): Double {
        return BigDecimal(numInDouble.toString()).setScale(2, RoundingMode.HALF_UP).toDouble()
    }


    fun encodeBase64(string: String): String? {
        val data: ByteArray = string.toByteArray(StandardCharsets.UTF_8)
        return Base64.encodeToString(data, Base64.NO_WRAP or Base64.URL_SAFE)
    }

    @SuppressLint("HardwareIds")
    fun generateID(context: Context): String {
        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return if (androidId != null && androidId != "9774d56d682e549c") {
            androidId
        } else {
            UUID.randomUUID().toString()
        }
    }

    fun initRecyclerViewGrid(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        configRecyclerView(view, GridLayoutManager(AppApplication.instance?.applicationContext, 3))
        view.adapter = adapter
    }

    fun getTechresColorList(): List<Int> {
        return arrayListOf(
            Color.rgb(187, 0, 20),
            Color.rgb(255, 139, 0),
            Color.rgb(56, 192, 93),
            Color.rgb(102, 170, 214),
            Color.rgb(0, 98, 5),
            Color.rgb(0, 113, 187),
            Color.rgb(217, 80, 138),
            Color.rgb(254, 149, 7),
            Color.rgb(254, 247, 120),
            Color.rgb(106, 167, 134),
            Color.rgb(53, 194, 209),
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53),
            Color.rgb(207, 248, 246),
            Color.rgb(148, 212, 212),
            Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175),
            Color.rgb(42, 109, 130)
        )
    }

    var EMOJI_FILTER =
        InputFilter { source, start, end, _, _, _ ->
            for (index in start until end) {
                val type = Character.getType(source[index])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return@InputFilter ""
                }
            }
            null
        }

    val specialCharacters = InputFilter { source, start, end, _, _, _ ->
        for (i in start until end) {
            if (source[i].toString() == " " || source[i].toString() == "," || source[i].toString() == "." || source[i].toString() == "\n") {
                // Do nothing
            } else if (!Character.isLetterOrDigit(source[i])) {
                return@InputFilter ""
            }
        }
        null
    }


    fun getCompleteAddressString(context: Context, latitude: Double, longitude: Double): String {
        var strAdd = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strAdd
    }


    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    fun calculateTotalPage(totalRecord: Int, limit: Int): Int {
        return if (totalRecord % limit == 0) {
            (totalRecord / limit)
        } else {
            (totalRecord / limit) + 1
        }
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun formatTwoInt(number: Int): String {
        return if (number <= 0) "00" else if (number < 10) "0$number" else number.toString()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun AppEditText.enableScrollText() {
        overScrollMode = View.OVER_SCROLL_ALWAYS
        scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
        isVerticalScrollBarEnabled = true
        setOnTouchListener { _, event ->
            if (!text.isNullOrEmpty()) {
                parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
    }

    fun View.clickWithDebounce(debounceTime: Long = 1000L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }

    fun checkMimeTypeVideo(type: Int): Boolean {
        return type == 1
    }

    fun getMimeType(url: String): String {
        try {
            return url.substring(url.lastIndexOf(".") + 1)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getRandomString(len: Int): String {
        val AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val rnd = SecureRandom()
        val sb = java.lang.StringBuilder(len)
        for (i in 0 until len) sb.append(AB[rnd.nextInt(AB.length)])
        return sb.toString()
    }

    fun getNameFileFormatTime(path: String): String {
        return String.format(
            "%s.%s",
            System.currentTimeMillis()
                .toString() + getRandomString(24),
            getMimeType(path)
        )
    }

    fun dpToPx(context: Context, valueInDp: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }

    fun getVideo(url: String, context: Context): String? {
        var link: String? = ""
        val a: Int
        val b: Int
        if (url.contains(context.getString(vn.techres.android.supplier.R.string.link_youtube_1))) {
            a = url.indexOf(".be/")
            link = url.substring(a + 4)
        } else if (url.contains(context.getString(vn.techres.android.supplier.R.string.link_youtube_4))) {
            a = url.indexOf("?v=")
            if (url.contains("&")) {
                b = url.indexOf("&")
                link = url.substring(a + 3, b)
            } else link = url.substring(a + 3)
        } else if (url.contains(context.getString(vn.techres.android.supplier.R.string.link_youtube_3))) {
            a = url.indexOf("?")
            b = url.indexOf("shorts/")
            link = url.substring(b + 7, a)
        }
        Timber.d("load link id : ")
        Timber.d(link)
        return link
    }

    fun generateQRCode(idGroup: String): Bitmap? {
        return try {
            val result: BitMatrix = QRCodeWriter().encode(
                String.format(
                    "%s",
                    idGroup
                ), BarcodeFormat.QR_CODE, 1024, 1024
            )
            val bitmap: Bitmap = Bitmap.createBitmap(
                result.width,
                result.height,
                Bitmap.Config.ARGB_8888
            )
            for (y in 0 until result.height) {
                for (x in 0 until result.width) {
                    if (result.get(x, y)) {
                        bitmap.setPixel(x, y, Color.BLACK)
                    }
                }
            }
            bitmap
        } catch (e: WriterException) {
            Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888)
        }
    }

    fun removeVietnameseFromString(str: String): String {
        val slug: String = try {
            val temp: String = Normalizer.normalize(str, Normalizer.Form.NFD)
            val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
            pattern.matcher(temp).replaceAll("")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
        return slug
    }

    fun getFirstLetterEachWord(string: String): String {
        var result = ""
        val stringText = string.split(" ")
        for (i in stringText.indices) {
            if (stringText[i].isNotEmpty()) { // Kiểm tra độ dài của chuỗi
                result += stringText[i].substring(0, 1).lowercase()
            }
        }
        return result
    }

}