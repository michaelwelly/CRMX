package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("SELECT w FROM Worker w " +
            "LEFT JOIN FETCH w.tasks " +
            "LEFT JOIN FETCH w.fieldServiceTeams")
    List<Worker> findAllWithTasksAndTeams();

    @Query("SELECT w FROM Worker w " +
            "LEFT JOIN FETCH w.tasks " +
            "LEFT JOIN FETCH w.fieldServiceTeams " +
            "WHERE w.id = :id")
    Optional<Worker> findByIdWithTasksAndTeams(Long id);
}