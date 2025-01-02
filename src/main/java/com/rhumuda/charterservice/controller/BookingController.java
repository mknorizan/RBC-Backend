package com.rhumuda.charterservice.controller;

import com.rhumuda.charterservice.dto.BookingDTO;
import com.rhumuda.charterservice.dto.BookingResponse;
import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(new BookingResponse(booking));
    }
} 