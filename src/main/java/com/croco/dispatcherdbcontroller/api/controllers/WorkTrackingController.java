package com.croco.dispatcherdbcontroller.api.controllers;

import com.croco.dispatcherdbcontroller.api.clients.UserService;
import com.croco.dispatcherdbcontroller.api.clients.WorkTrackingService;
import com.croco.dispatcherdbcontroller.dto.UserDto;
import com.croco.dispatcherdbcontroller.service.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

import static com.croco.dispatcherdbcontroller.utils.DataUtils.convertStringToOffsetDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/worktracking")
public class WorkTrackingController {

    private final WorkTrackingService workTrackingService;
    private final RequestValidator requestValidator;

    @PostMapping("worktime/{id}")
    public ResponseEntity<Void> givenWorkTime(@PathVariable Long id,
                                                 @RequestParam(required = false) Boolean action,
                                                 @RequestParam(required = false) String session,
                                                 @RequestParam(required = false) String date,
                                                 @RequestHeader(value = "Version", defaultValue = "1.0") String version,
                                                 @RequestHeader("Authorization") String authToken,
                                                 @RequestHeader("MD5-Signature") String md5Signature) {
        requestValidator.validate(version, authToken, md5Signature);

        OffsetDateTime dateTime = convertStringToOffsetDateTime(date);
        workTrackingService.givenWorkTime(id, action, session, dateTime);
        return ResponseEntity.noContent().build();
    }
}
