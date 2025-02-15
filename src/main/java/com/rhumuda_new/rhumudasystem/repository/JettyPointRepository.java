package com.rhumuda_new.rhumudasystem.repository;

import com.rhumuda_new.rhumudasystem.entity.JettyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JettyPointRepository extends JpaRepository<JettyPoint, Long> {
    List<JettyPoint> findByIsActiveTrue();
} 