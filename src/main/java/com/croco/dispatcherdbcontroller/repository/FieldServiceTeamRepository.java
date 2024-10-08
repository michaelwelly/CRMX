package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.FieldServiceTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldServiceTeamRepository extends JpaRepository<FieldServiceTeam, Long> {
}