package com.rhumuda.charterservice.repository;

import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingId(String bookingId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);
    List<Booking> findByCustomerEmail(String email);
}