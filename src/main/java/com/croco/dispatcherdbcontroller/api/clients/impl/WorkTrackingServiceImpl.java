package com.croco.dispatcherdbcontroller.api.clients.impl;

import com.croco.dispatcherdbcontroller.api.clients.WorkTrackingService;
import com.croco.dispatcherdbcontroller.entity.WorkTime;
import com.croco.dispatcherdbcontroller.mapper.WorkerMapper;
import com.croco.dispatcherdbcontroller.repository.WorkTrackingRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class WorkTrackingServiceImpl implements WorkTrackingService {

    private final WorkTrackingRepository workTrackingRepository;
    private final WorkerMapper workerMapper;
    @Override
    public void givenWorkTime(Long id, Boolean action, String session, OffsetDateTime dateTime) {
        val workTime = WorkTime.builder()
                .userId(id)
                .action(action)
                .session(session)
                .dateTime(dateTime)
                .build();
        workTrackingRepository.save(workTime);
    }
}
