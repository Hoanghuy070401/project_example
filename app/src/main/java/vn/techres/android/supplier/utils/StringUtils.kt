package vn.techres.android.supplier.utils

import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets

/**
 * Created by kylin on 2017/4/6.
 */
object StringUtils {
    /**
     * string to byte[]
     */
    fun strTobytes(str: String): ByteArray? {
        var b: ByteArray? = null
        var data: ByteArray? = null
        try {
            b = str.toByteArray(StandardCharsets.UTF_8)
            data = String(b, StandardCharsets.UTF_8).toByteArray(charset("gbk"))
        } catch (e: UnsupportedEncodingException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return data
    }

    /**
     * byte[] merger
     */
    fun byteMerger(byte_1: ByteArray, byte_2: ByteArray): ByteArray {
        val byte_3 = ByteArray(byte_1.size + byte_2.size)
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.size)
        System.arraycopy(byte_2, 0, byte_3, byte_1.size, byte_2.size)
        return byte_3
    }

    fun strTobytes(str: String, charset: String?): ByteArray? {
        var b: ByteArray? = null
        var data: ByteArray? = null
        try {
            b = str.toByteArray(StandardCharsets.UTF_8)
            data = String(b, StandardCharsets.UTF_8).toByteArray(charset(charset!!))
        } catch (e: UnsupportedEncodingException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return data
    }
}