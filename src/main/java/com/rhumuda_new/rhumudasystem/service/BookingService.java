package com.rhumuda_new.rhumudasystem.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.rhumuda_new.rhumudasystem.dto.BookingDTO;
import com.rhumuda_new.rhumudasystem.entity.Booking;
import com.rhumuda_new.rhumudasystem.repository.BookingRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;

    public BookingDTO getBookingById(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setFirstName(booking.getFirstName());
        dto.setLastName(booking.getLastName());
        dto.setEmail(booking.getEmail());
        dto.setPhoneNumber(booking.getPhoneNumber());
        dto.setAddressLine1(booking.getAddressLine1());
        dto.setAddressLine2(booking.getAddressLine2());
        dto.setPostalCode(booking.getPostalCode());
        dto.setCity(booking.getCity());
        dto.setCountry(booking.getCountry());
        dto.setBookingDate(booking.getBookingDate());
        dto.setPassengers(booking.getPassengers());
        dto.setJettyPoint(booking.getJettyPoint().getId().toString());
        dto.setPackageId(booking.getPackageDetails().getId().toString());
        dto.setPackageName(booking.getPackageDetails().getName());
        dto.setCategoryName(booking.getPackageDetails().getCategory().getName());
        dto.setStatus(booking.getStatus().name());
        
        return dto;
    }

    @Transactional
    public void updateBookingStatus(String bookingId, String status) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        
        booking.setStatus(Booking.BookingStatus.valueOf(status));
        bookingRepository.save(booking);
    }
} 