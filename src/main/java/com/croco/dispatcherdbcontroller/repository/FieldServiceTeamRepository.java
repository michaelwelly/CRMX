package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.FieldServiceTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldServiceTeamRepository extends JpaRepository<FieldServiceTeam, Long> {

    @Query("SELECT fst FROM FieldServiceTeam fst JOIN FETCH fst.workers w WHERE fst.id = :id")
    Optional<FieldServiceTeam> getOneWithWorkers(@Param("id") Long id);


    @Query("SELECT fst FROM FieldServiceTeam fst JOIN FETCH fst.workers w")
    List<FieldServiceTeam> findAllWithWorkers();
}