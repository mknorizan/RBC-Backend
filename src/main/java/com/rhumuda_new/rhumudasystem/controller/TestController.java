package com.rhumuda_new.rhumudasystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rhumuda_new.rhumudasystem.service.EmailService;
import com.rhumuda_new.rhumudasystem.dto.BookingDTO;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email")
    public ResponseEntity<?> testEmail() {
        try {
            // Create a test BookingDTO
            BookingDTO testBooking = new BookingDTO();
            testBooking.setBookingId("TEST-001");
            testBooking.setFirstName("Test");
            testBooking.setLastName("User");
            testBooking.setEmail("amirul.ahj@gmail.com");  // Your email for testing
            testBooking.setPhoneNumber("0123456789");
            testBooking.setAddressLine1("Test Address");
            testBooking.setPostalCode("12345");
            testBooking.setCity("Test City");
            testBooking.setCountry("Malaysia");
            testBooking.setBookingDate(LocalDate.now());
            testBooking.setPassengers(2);
            testBooking.setJettyPoint("Test Jetty");
            testBooking.setPackageId("PKG-001");

            emailService.sendBookingEmails(testBooking);
            return ResponseEntity.ok().body("Test email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send test email: " + e.getMessage());
        }
    }
} 