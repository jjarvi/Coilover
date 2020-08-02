package com.example.coilover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "events")
data class Event(
    @ColumnInfo(name = "odometer") val odometer: Int?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "amount") val amount: Double?,
    @ColumnInfo(name = "type") val type: EventType?,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0) {
}
