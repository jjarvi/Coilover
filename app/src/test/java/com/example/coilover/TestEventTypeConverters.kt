package com.example.coilover

import org.junit.Test
import org.junit.Assert.*
import java.security.InvalidParameterException


class TestEventTypeConverters {

    @Test
    fun fromInt() {
        assertEquals(EventType.Refuel,    EventTypeConverters.intToType(0));
        assertEquals(EventType.Wash,      EventTypeConverters.intToType(1));
        assertEquals(EventType.Service,   EventTypeConverters.intToType(2));
        assertEquals(EventType.Product,   EventTypeConverters.intToType(3));
        assertEquals(EventType.SparePart, EventTypeConverters.intToType(4));
    }

    @Test
    fun fromString() {
        assertEquals(EventType.Refuel,    EventTypeConverters.stringToType("refuel"));
        assertEquals(EventType.Wash,      EventTypeConverters.stringToType("wash"));
        assertEquals(EventType.Service,   EventTypeConverters.stringToType("service"));
        assertEquals(EventType.Product,   EventTypeConverters.stringToType("product"));
        assertEquals(EventType.SparePart, EventTypeConverters.stringToType("sparepart"));
    }

    @Test
    fun fromStringCase() {
        assertEquals(EventType.Refuel,    EventTypeConverters.stringToType("rEfUel"));
    }

    @Test
    fun invalidInput() {
        assertThrows(InvalidParameterException::class.java) {
            EventTypeConverters.stringToType("");
        }
    }
}
