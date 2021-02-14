package com.example.coilover

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "events")
data class Event(
    @ColumnInfo(name = "timestamp") val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "odometer") val odometer: Int = 0,
    @ColumnInfo(name = "price") val price: Double = 0.0,
    @ColumnInfo(name = "amount") val amount: Double = 0.0,
    @ColumnInfo(name = "type") val type: EventType = EventType.Refuel,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0) {

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Event) {
            return date == other.date &&
                    odometer == other.odometer &&
                    price == other.price &&
                    amount == other.amount &&
                    type == other.type &&
                    uid == other.uid;
        }
        return false;
    }
}

