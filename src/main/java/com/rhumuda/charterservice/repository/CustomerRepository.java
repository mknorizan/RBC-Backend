package com.rhumuda.charterservice.repository;

import com.rhumuda.charterservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Add custom queries if needed
} 