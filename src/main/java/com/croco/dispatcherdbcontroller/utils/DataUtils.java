package com.croco.dispatcherdbcontroller.utils;

import com.croco.dispatcherdbcontroller.dto.Address;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

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

    public static Address parseAddress(Map<String, Object> addressJson) {
        Address address = new Address();
        String cityValue = (String) addressJson.get("City");
        String streetValue = (String) addressJson.get("Street");

        if (cityValue != null && !cityValue.isEmpty()) {
            address.setCity(cityValue.trim());
        }

        if (streetValue != null) {
            streetValue = streetValue.replace(" ул ", " улица ")
                    .replace(" ул.", " улица ")
                    .replace("ул ", "улица ")
                    .replace("ул.", "улица ");

            if (cityValue == null || cityValue.isEmpty()) {
                String[] parts = streetValue.split(",", 2);
                if (parts.length > 0) {
                    address.setCity(parts[0].trim());
                }
                if (parts.length > 1) {
                    address.setStreet(parts[1].trim());
                }
            } else {
                address.setStreet(streetValue.trim());
            }
        }
        return address;
    }
}
