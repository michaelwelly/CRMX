package com.croco.dispatcherdbcontroller.utils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DataUtils {

    public static OffsetDateTime convertStringToOffsetDateTime(String dateString) {

        if (dateString != null){
            // Парсинг строки в LocalDate
            LocalDate localDate = LocalDate.parse(dateString);
            // Преобразование LocalDate в OffsetDateTime с заданным временем и временной зоной
            return localDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
        }
        else return null;
    }
}
