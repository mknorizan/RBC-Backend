package com.rhumuda.charterservice.dto;

import lombok.Data;
import java.util.Set;

@Data
public class BookingDTO {
    private String packageId;
    private String packageType;
    private String bookingDate;
    private int numberOfPassengers;
    private String jettyLocation;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String specialRequests;
    private Set<String> addOns;
    private String alternativeDate1;
    private String alternativeDate2;
    private CustomerAddressDTO customerAddress;

    @Data
    public static class CustomerAddressDTO {
        private String addressLine1;
        private String addressLine2;
        private String postalCode;
        private String city;
        private String country;
    }
} 