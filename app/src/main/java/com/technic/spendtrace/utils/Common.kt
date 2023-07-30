package com.technic.spendtrace.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Common {

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        return sdf.format(Date())
    }

    fun getCurrentDate(format: DateFormats? = null): String {
        val sdf = if (format == null) {
            SimpleDateFormat("MM-dd-yyyy", Locale.US)
        } else {
            when (format) {
                DateFormats.Month -> SimpleDateFormat("MM", Locale.US)
                DateFormats.Year -> SimpleDateFormat("yyyy", Locale.US)
            }
        }

        return sdf.format(Date())
    }

    //ToDo: make determineNextMonth() and determinePreviousMonth() a single method
    fun determineNextMonth(): String {
        val cMonth = getCurrentDate(DateFormats.Month).toInt()
        val nextMonth = if (cMonth < 12) {
            cMonth + 1
        } else {
            1
        }

        val year = if (nextMonth == 1) {
            getCurrentDate(DateFormats.Year).toInt() + 1
        } else {
            getCurrentDate(DateFormats.Year).toInt()
        }
        val formatMonth = if (nextMonth < 10) {
            "0$nextMonth"
        } else {
            nextMonth
        }

        val formatStr = "$formatMonth-01-$year"
        Log.i("DateFormats", "determineNextMonth() returned $formatStr")
        return formatStr
    }

    fun determinePreviousMonth(currentMonth: String? = null): String {
        val cMonth = currentMonth?.toInt() ?: getCurrentDate(DateFormats.Month).toInt()
//        val cMonth = getCurrentDate(DateFormats.Month).toInt()
        val prevMonth = if (cMonth > 1) {
            cMonth - 1
        } else {
            12
        }

        val year = if (prevMonth == 12) {
            getCurrentDate(DateFormats.Year).toInt() - 1
        } else {
            getCurrentDate(DateFormats.Year).toInt()
        }
        val formatMonth = if (prevMonth < 10) {
            "0$prevMonth"
        } else {
            prevMonth
        }

        val formatStr = "$formatMonth-01-$year"
        Log.i("DateFormats", "determinePreviousMonth() returned $formatStr")

        return formatStr
    }

    fun getDateStamp(): Int {
        val month = extractMonth(getCurrentDate())
        val year = extractYear(getCurrentDate())

        val concatStr = "$year$month"

        return concatStr.toInt()
    }

    fun extractMonth(dateStr: String): String {
        return dateStr.substringBefore("-")
    }

    fun extractYear(dateStr: String): String {
        return dateStr.substringAfterLast("-")
    }

    fun formatPrice(price: Double): String {
        //ToDo: see if the decimal amount appears if the amount is .00
        return "$ $price"
    }



}

enum class DateFormats {
    Month,
    Year
}