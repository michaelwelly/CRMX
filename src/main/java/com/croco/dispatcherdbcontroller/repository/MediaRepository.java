package com.croco.dispatcherdbcontroller.repository;

import com.croco.dispatcherdbcontroller.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
}