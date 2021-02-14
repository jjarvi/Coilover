package com.example.coilover.ui.add

import com.example.coilover.Event
import com.example.coilover.EventTypeConverters
import java.security.InvalidParameterException
import java.time.LocalDate
import java.time.format.DateTimeParseException


class EventCsvParser {

    enum class Status {
        Ok,
        InvalidDate,
        InvalidInput,
    }

    data class Result(
        val status: Status = Status.InvalidInput,
        val event: Event = Event()
    ) {
    }

    companion object {

        // CSV format:
        //     0    1    2   3     4      5        6
        //     type;date;odo;amount;price;location;description

        private const val NUM_COLUMNS = 7
        private const val DELIMITER = ';'
        private const val TYPE_INDEX = 0
        private const val DATE_INDEX = 1
        private const val ODO_INDEX = 2
        private const val AMOUNT_INDEX = 3
        private const val PRICE_INDEX = 4

        fun parse(line: String): Result {
            val items = line.split(DELIMITER).toMutableList()
            items.replaceAll(String::trim)

            if (items.size != NUM_COLUMNS) {
                return Result(Status.InvalidInput)
            }
            try {
                return Result(
                    Status.Ok,
                    Event(
                        LocalDate.parse(items[DATE_INDEX]),
                        getInt(items[ODO_INDEX]),
                        getDouble(items[AMOUNT_INDEX]),
                        getDouble(items[PRICE_INDEX]),
                        EventTypeConverters.stringToType(items[TYPE_INDEX])
                    )
                )
            }
            catch (exception: DateTimeParseException) {
                return Result(Status.InvalidDate)
            }
            catch (exception: InvalidParameterException) {
                return Result(Status.InvalidInput)
            }
        }

        private fun getInt(str: String): Int {
            return if (str.isEmpty()) 0 else str.toInt()
        }

        private fun getDouble(str: String): Double {
            return if (str.isEmpty()) 0.0 else str.replace(",", ".").toDouble()
        }
    }
}
