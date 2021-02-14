package com.example.coilover

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun dateToLong(date: LocalDate): Long {
            return date.toEpochDay();
        }

        @TypeConverter
        @JvmStatic
        fun longToDate(value: Long): LocalDate {
            return LocalDate.ofEpochDay(value);
        }
    }

}