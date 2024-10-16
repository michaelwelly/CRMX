package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Incident;
import com.croco.dispatcherdbcontroller.entity.IncidentStatus;
import com.croco.dispatcherdbcontroller.entity.IncidentType;
import com.croco.dispatcherdbcontroller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long>, JpaSpecificationExecutor<Incident> {


    @Query("SELECT i FROM Incident i " +
            "JOIN FETCH i.reporter " +
            "JOIN FETCH i.user " +
            "JOIN FETCH i.filial " +
            "JOIN FETCH i.tasks " +
            "JOIN FETCH i.team " +
            "WHERE i.id = :id")
    Optional<Incident> findIncident(Long id);


    @Query("SELECT i FROM Incident i " +
            "JOIN FETCH i.reporter " +
            "JOIN FETCH i.user " +
            "JOIN FETCH i.filial " +
            "JOIN FETCH i.tasks " +
            "JOIN FETCH i.team " +
            "WHERE i.id IN :ids")
    List<Incident> findAllByIdWithDetails(@Param("ids") List<Long> ids);

    /**
     * Ищет инциденты по заданному статусу.
     *
     * @param incidentStatus статус инцидента для фильтрации
     * @return список инцидентов с указанным статусом.
     */
    Optional<List<Incident>> findByIncidentStatus(IncidentStatus incidentStatus);

    /**
     * Ищет инциденты по указанным статусам.
     *
     * @param statuses список статусов инцидента для фильтрации
     * @return список инцидентов, соответствующих одному из указанных статусов.
     */
    @Query("SELECT i FROM Incident i WHERE i.incidentStatus IN :statuses")
    Optional<List<Incident>> findByIncidentStatusIn(@Param("statuses") List<IncidentStatus> statuses);

    // Фильтр по оператору
    List<Incident> findByUser(User user);

    @Query("SELECT i FROM Incident i WHERE " +
            "(:startDateTime IS NULL OR i.registrationDttm >= :startDateTime) " +
            "AND (:endDateTime IS NULL OR i.registrationDttm <= :endDateTime) " +
            "AND (i.user.id = :userId)")
    List<Incident> findByRegistrationDttmBetweenAndUser(
            @Param("startDateTime") OffsetDateTime startDateTime,
            @Param("endDateTime") OffsetDateTime endDateTime,
            @Param("userId") Long userId);

    @Query("SELECT i FROM Incident i WHERE " +
            "(:startDateTime IS NULL OR i.registrationDttm >= :startDateTime) " +
            "AND (:endDateTime IS NULL OR i.registrationDttm <= :endDateTime) " +
            "AND (:statuses IS NULL OR i.incidentStatus IN :statuses)")
    List<Incident> findByRegistrationDttmBetweenAndStatusIn(
            @Param("startDateTime") OffsetDateTime startDate,
            @Param("endDateTime") OffsetDateTime endDate,
            @Param("statuses") List<IncidentStatus> statuses);

    @Query("SELECT i FROM Incident i WHERE " +
            "(:startDateTime IS NULL OR i.registrationDttm >= :startDateTime) " +
            "AND (:endDateTime IS NULL OR i.registrationDttm <= :endDateTime)")
    List<Incident> findByRegistrationDttmBetween(
            @Param("startDateTime") OffsetDateTime startDateTime,
            @Param("endDateTime") OffsetDateTime endDateTime);

    @Query("SELECT i FROM Incident i WHERE " +
            "(:statuses IS NULL OR i.incidentStatus IN :statuses)")
    List<Incident> findByStatusIn(
            @Param("statuses") List<IncidentStatus> statuses);

    @Query("SELECT i FROM Incident i WHERE " +
            "(:startDateTime IS NULL OR i.registrationDttm >= :startDateTime) " +
            "AND (:endDateTime IS NULL OR i.registrationDttm <= :endDateTime) " +
            "AND (:user IS NULL OR i.user = :user) " +
            "AND (:statuses IS NULL OR i.incidentStatus IN :statuses)")
    List<Incident> findByRegistrationDttmBetweenAndUserIdAndStatusIn(
            @Param("startDateTime") OffsetDateTime startDateTime,
            @Param("endDateTime") OffsetDateTime endDateTime,
            @Param("user") User user,
            @Param("statuses") List<IncidentStatus> statuses);

    @Query( "SELECT i FROM Incident i WHERE " +
            "(:user IS NULL OR i.user = :user) " +
            "AND (:statuses IS NULL OR i.incidentStatus IN :statuses)")
    List<Incident> findByUserIdAndStatusIn(
            @Param("user") User user,
            @Param("statuses") List<IncidentStatus> statuses);


    // Фильтр по тегу
    List<Incident> findByIncidentStatusAndIncidentType(IncidentStatus incidentStatus, IncidentType incidentType);

}