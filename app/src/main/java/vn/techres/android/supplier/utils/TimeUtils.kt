package vn.techres.android.supplier.utils

import android.annotation.SuppressLint
import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.Objects
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object TimeUtils {
    fun timeAgoChat(context: Context, date: String): String {
        val past = getDateFromString(date)
        val now = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = past!!
        try {
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time) //giây
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time) //phút
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time) //giờ
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time) // ngày
            val year = days / 365 //năm
            return if (seconds < 60) {
                context.getString(vn.techres.android.supplier.R.string.finish_now)
            } else if (minutes < 60) {
                String.format(context.getString(vn.techres.android.supplier.R.string.minute_ago), minutes)
            } else if (hours < 24) {
                String.format(context.getString(vn.techres.android.supplier.R.string.hour_ago), hours)
            } else if (days < 8) {
                String.format(context.getString(vn.techres.android.supplier.R.string.day_ago), days)
            } else if (days in 8..364) {
                String.format(
                        "%s/%s lúc %s:%s",
                        String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)),
                        String.format("%02d", calendar.get(Calendar.MONTH) + 1),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        String.format("%02d", calendar.get(Calendar.MINUTE))
                )
            } else if (year > 0) {
                String.format(
                        "%s lúc %s", date, calendar.get(Calendar.HOUR_OF_DAY), String.format(
                        "%02d", calendar.get(
                        Calendar.MINUTE
                )
                )
                )
            } else {
                String.format("%s", date)
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return String.format("%s", date)
    }

    private fun getDateFromString(strDate: String): Date? {
        val old = "MM-dd-yyyy HH:mm:ss"
        val new = "HH:mm dd-MM-yyyy"
        @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat(old)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = try {
            df.parse(strDate)!!
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }
        df.timeZone = TimeZone.getDefault()
        val formattedDate = df.format(Objects.requireNonNull(date))
        var newDateString = ""
        try {
            @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat(old)
            val d = sdf.parse(formattedDate)
            sdf.applyPattern(new)
            newDateString = sdf.format(Objects.requireNonNull(d))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val dateFormat = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
        var dateData: Date? = null
        try {
            dateData = dateFormat.parse(newDateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateData
    }

    fun timeAgoStatus(date: String): String {
        val past = getDateFromString(date)
        val now = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = past!!
        try {
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time) //giây
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time) //phút
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time) //giờ
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time) // ngày
            val year = days / 365 //năm
            return if (seconds < 60) {
                "Vừa truy cập"
            } else if (minutes < 60) {
                String.format("%s %s phút trước", "Hoạt động", minutes)
            } else if (hours < 24) {
                String.format("%s %s giờ trước", "Hoạt động", hours)
            } else if (days < 8) {
                String.format("%s %s ngày trước", "Hoạt động", days)
            } else if (days in 8..365) {
                String.format("%s %s tuần trước", "Hoạt động", days / 7)
            } else if (year > 365) {
                String.format("%s %s năm trước", "Hoạt động", calendar.get(Calendar.YEAR))
            } else {
                String.format("%s", date)
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return String.format("%s", date)
    }

    fun changeFormatTimeMessageChat(strDate: String): String {
        val old = "MM-dd-yyyy HH:mm:ss"
        val new = "dd/MM/yyyy HH:mm:ss"
        @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat(old)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = try {
            df.parse(strDate)!!
        } catch (e: ParseException) {
            throw java.lang.RuntimeException(e)
        }
        df.timeZone = TimeZone.getDefault()
        val formattedDate = df.format(Objects.requireNonNull(date))
        var newDateString = ""
        try {
            @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat(old)
            val d = sdf.parse(formattedDate)
            sdf.applyPattern(new)
            newDateString = sdf.format(Objects.requireNonNull(d))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return newDateString
    }
}
