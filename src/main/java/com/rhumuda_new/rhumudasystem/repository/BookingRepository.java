package com.rhumuda_new.rhumudasystem.repository;

import com.rhumuda_new.rhumudasystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByBookingId(String bookingId);
    Optional<Booking> findByBookingId(String bookingId);
}