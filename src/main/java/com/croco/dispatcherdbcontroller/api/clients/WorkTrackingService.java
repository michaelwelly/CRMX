package com.croco.dispatcherdbcontroller.api.clients;

import com.croco.dispatcherdbcontroller.dto.UserDto;

import java.time.OffsetDateTime;

public interface WorkTrackingService {
    void givenWorkTime(Long id, Boolean action, String session, OffsetDateTime dateTime);
}
