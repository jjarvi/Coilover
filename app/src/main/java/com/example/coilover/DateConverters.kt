package com.example.coilover

import androidx.room.TypeConverter
import java.util.*

class DateConverters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun dateToLong(date: Date): Long {
            return date.time
        }

        @TypeConverter
        @JvmStatic
        fun longToDate(value: Long): Date {
            return Date(value)
        }
    }

}