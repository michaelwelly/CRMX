package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    Optional<Map> findByTitleStrAndDescriptionTxt(String city, String street);
}