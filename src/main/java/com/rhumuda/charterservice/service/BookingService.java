package com.rhumuda.charterservice.service;

import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.model.BookingStatus;
import com.rhumuda.charterservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EmailService emailService;

    public Booking createBooking(Booking booking) {
        // Generate unique booking ID
        booking.setBookingId(generateBookingId());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);
        
        Booking savedBooking = bookingRepository.save(booking);
        
        // Send confirmation emails
        emailService.sendCustomerConfirmation(savedBooking);
        emailService.sendAdminNotification(savedBooking);
        
        return savedBooking;
    }

    public Booking cancelBooking(String bookingId, String reason) {
        Booking booking = bookingRepository.findByBookingId(bookingId);
        if (booking != null) {
            booking.setStatus(BookingStatus.CANCELLED);
            booking.setCancellationReason(reason);
            return bookingRepository.save(booking);
        }
        return null;
    }

    private String generateBookingId() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 