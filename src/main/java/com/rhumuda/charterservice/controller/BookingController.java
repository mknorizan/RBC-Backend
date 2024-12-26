package com.rhumuda.charterservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody String rawJson) {
        logger.info("Received raw JSON: {}", rawJson);
        try {
            Booking booking = objectMapper.readValue(rawJson, Booking.class);
            Booking result = bookingService.createBooking(booking);
            logger.info("Successfully created booking: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error creating booking: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable String bookingId,
            @RequestParam String reason) {
        Booking cancelledBooking = bookingService.cancelBooking(bookingId, reason);
        if (cancelledBooking != null) {
            return ResponseEntity.ok(cancelledBooking);
        }
        return ResponseEntity.notFound().build();
    }
} 