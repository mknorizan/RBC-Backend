package com.rhumuda.charterservice.dto;

import com.rhumuda.charterservice.model.Booking;
import lombok.Data;

@Data
public class BookingResponse {
    private String bookingId;
    private String status;
    private String message;

    public BookingResponse(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.status = booking.getStatus().toString();
        this.message = "Booking created successfully";
    }
} 