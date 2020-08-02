package com.example.coilover

import androidx.room.TypeConverter


class EventTypeConverters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun typeToInt(type: EventType): Int {
            return type.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun intToType(value: Int): EventType {
            return when(value) {
                0 -> EventType.Refuel
                1 -> EventType.Wash
                2 -> EventType.Service
                3 -> EventType.Product
                else -> EventType.SparePart
            }
        }
    }
}