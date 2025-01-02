package com.rhumuda.charterservice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {
    private String packageId;
    private String packageType;
    private LocalDateTime bookingDate;
    private int numberOfPassengers;
    private String jettyLocation;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String specialRequests;
    private Set<String> addOns;
    private LocalDateTime alternativeDate1;
    private LocalDateTime alternativeDate2;
} 