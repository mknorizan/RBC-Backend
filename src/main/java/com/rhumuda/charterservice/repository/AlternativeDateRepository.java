package com.rhumuda.charterservice.repository;

import com.rhumuda.charterservice.model.AlternativeDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlternativeDateRepository extends JpaRepository<AlternativeDate, Integer> {
} 