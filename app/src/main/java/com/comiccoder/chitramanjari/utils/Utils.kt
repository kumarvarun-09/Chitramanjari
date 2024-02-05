package com.comiccoder.chitramanjari.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
    return sdf.format(Date())
}
fun getTimeStamp():Long
{
    return Calendar.getInstance(Locale.ENGLISH).timeInMillis
}
fun getDateTimeFromTimeStamp(timeStamp:Int): String
{
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = timeStamp * 1000L
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm");
    return sdf.format(cal.time);
}
