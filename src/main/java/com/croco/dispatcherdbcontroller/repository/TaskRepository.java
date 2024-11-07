package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.worker WHERE t.id = :id")
    Optional<Task> findOneWithWorker(@Param("id") Long id);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.worker")
    List<Task> findAllWithWorkers();

    Optional<Task> findByTitleStrAndOrderNum(String titleStr, Integer orderNum);

    @Query("SELECT COALESCE(MAX(t.id), 0) FROM Task t")
    Long findMaxId();
}