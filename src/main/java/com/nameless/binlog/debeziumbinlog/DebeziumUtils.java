package com.nameless.binlog.debeziumbinlog;

import io.debezium.engine.ChangeEvent;

public class DebeziumUtils {

    public static String getTable(ChangeEvent<String, String> event) {
        if (null == event || null == event.destination()) {
            return "";
        }
        return getTable(event.destination());
    }

    public static String getTable(String destination) {
        if (destination.isEmpty() || destination.isBlank()) {
            return "";
        }
        String[] data = destination.split("\\.");
        if (data.length < 1) {
            return "";
        }
        return data[data.length - 1];
    }
}
