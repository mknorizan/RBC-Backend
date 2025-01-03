package com.rhumuda.charterservice.controller;

import com.rhumuda.charterservice.dto.BookingDTO;
import com.rhumuda.charterservice.dto.BookingResponse;
import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            log.info("Received booking request: {}", bookingDTO);
            BookingResponse response = bookingService.createBooking(bookingDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error creating booking: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("error", "Failed to create booking");
            errorResponse.put("details", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body(errorResponse);
        }
    }
} 