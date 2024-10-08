package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Репозиторий для работы с филиалами
 */
@Repository
public interface FilialRepository extends JpaRepository<Filial, Long>, JpaSpecificationExecutor<Filial> {
    Optional<Filial> findByTitleStrAndDescriptionTxt(String titleStr, String descriptionTxt);

    @Query("SELECT COALESCE(MAX(f.id), 0) FROM Filial f")
    Long findMaxId();
}