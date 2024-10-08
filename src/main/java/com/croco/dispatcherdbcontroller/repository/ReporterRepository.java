package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporterRepository extends JpaRepository<Reporter, Long>, JpaSpecificationExecutor<Reporter> {
    /**
     * Ищет репортеров по подстроке в номере телефона.
     *
     * @param phoneSubstring подстрока для поиска в поле номера телефона
     * @return Optional, содержащий список репортеров, соответствующих критериям поиска.
     */
    Optional<List<Reporter>> findByPhoneStrContaining(String phoneSubstring);

    /**
     * Ищет репортеров по подстроке в адресе (городе и/или улице).
     *
     * @param addressSubstring подстрока для поиска в поле адреса
     * @return Optional, содержащий список репортеров, соответствующих критериям поиска.
     */
    Optional<List<Reporter>> findByDescriptionTxtContaining(String addressSubstring);

    Optional<Reporter> findByNameStrAndPhoneStr(String nameStr, String phoneStr);

    @Query("SELECT COALESCE(MAX(r.id), 0) FROM Reporter r")
    Long findMaxId();
}