package com.rhumuda.charterservice.service;

import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.model.Customer;
import com.rhumuda.charterservice.repository.BookingRepository;
import com.rhumuda.charterservice.dto.BookingDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EmailService emailService;
    
    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setBookingId("BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        Customer customer = new Customer();
        customer.setFirstName(bookingDTO.getCustomerName());
        customer.setEmail(bookingDTO.getCustomerEmail());
        customer.setPhoneNumber(bookingDTO.getCustomerPhone());
        
        booking.setCustomer(customer);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setNumberOfPassengers(bookingDTO.getNumberOfPassengers());
        booking.setJettyLocation(bookingDTO.getJettyLocation());
        booking.setSpecialRequests(bookingDTO.getSpecialRequests());
        booking.setAddOns(bookingDTO.getAddOns());
        booking.setAlternativeDate1(bookingDTO.getAlternativeDate1());
        booking.setAlternativeDate2(bookingDTO.getAlternativeDate2());
        
        Booking savedBooking = bookingRepository.save(booking);
        
        try {
            emailService.sendBookingConfirmation(customer.getEmail(), savedBooking.getBookingId());
        } catch (Exception e) {
            // Log error but don't fail booking creation
            e.printStackTrace();
        }
        
        return savedBooking;
    }
} 