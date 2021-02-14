package com.example.coilover

import com.example.coilover.ui.add.EventCsvParser
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate


class TestEventCsvParser {

    @Test
    fun invalidInput() {
        assertResult(
            Event(),
            EventCsvParser.Status.InvalidInput,
            EventCsvParser.parse("refuel;1.1.2021;200000;44,07")
        )
    }

    private fun assertResult(
        expectedEvent: Event,
        expectedStatus: EventCsvParser.Status,
        result: EventCsvParser.Result
    ) {
        assertEquals(expectedEvent, result.event)
        assertEquals(expectedStatus, result.status)
    }

    @Test
    fun refuel() {
        assertResult(
            Event(LocalDate.of(2020, 12, 7), 25000, 30.59, 44.07, EventType.Refuel),
            EventCsvParser.Status.Ok,
            EventCsvParser.parse("refuel   ; 2020-12-07 ; 25000 ;30,59; 44,07; location ;  description  ")
        )
    }

    @Test
    fun wash() {
        assertResult(
            Event(LocalDate.of(2020, 2, 10), 10000, 0.0, 0.0, EventType.Wash),
            EventCsvParser.Status.Ok,
            EventCsvParser.parse("wash;2020-02-10;10000; ; ;Foo;")
        )
    }

    @Test
    fun sparePart() {
        assertResult(
            Event(LocalDate.of(2020, 1, 11), 200123, 19.9, 0.0, EventType.SparePart),
            EventCsvParser.Status.Ok,
            EventCsvParser.parse("sparepart; 2020-01-11;  200123 ; 19.90 ;; Bar;  Windscreen wipers")
        )
    }
}