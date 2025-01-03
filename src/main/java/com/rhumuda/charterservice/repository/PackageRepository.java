package com.rhumuda.charterservice.repository;

import com.rhumuda.charterservice.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    // Add custom queries if needed
} 