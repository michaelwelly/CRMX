package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTrackingRepository extends JpaRepository<WorkTime, Long> {
}
