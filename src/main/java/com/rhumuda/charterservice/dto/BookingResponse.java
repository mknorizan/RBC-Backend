package com.rhumuda.charterservice.dto;

import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.model.Customer;
import com.rhumuda.charterservice.model.Package;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Data
public class BookingResponse {
    private String bookingId;
    private String status;
    private String message;
    private CustomerDetails customer;
    private ReservationDetails reservation;
    private OtherOptions otherOptions;

    @Data
    public static class CustomerDetails {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String addressLine1;
        private String addressLine2;
        private String postalCode;
        private String city;
        private String country;
    }

    @Data
    public static class ReservationDetails {
        private String jettyLocation;
        private int numberOfPassengers;
        private LocalDateTime bookingDate;
        private String packageType;
        private PackageDetails packageDetails;
        private Set<String> addOns = new HashSet<>();
    }

    @Data
    public static class OtherOptions {
        private LocalDateTime alternativeDate1;
        private LocalDateTime alternativeDate2;
        private String remarks;
    }

    public BookingResponse(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        
        this.bookingId = booking.getBookingId();
        this.status = booking.getStatus().toString();
        this.message = "Booking created successfully";
        
        // Set customer details with null checks
        Customer bookingCustomer = booking.getCustomer();
        if (bookingCustomer != null) {
            this.customer = new CustomerDetails();
            this.customer.setFirstName(bookingCustomer.getFirstName());
            this.customer.setLastName(bookingCustomer.getLastName());
            this.customer.setEmail(bookingCustomer.getEmail());
            this.customer.setPhoneNumber(bookingCustomer.getPhone());
            this.customer.setAddressLine1(bookingCustomer.getAddressLine1());
            this.customer.setAddressLine2(bookingCustomer.getAddressLine2());
            this.customer.setPostalCode(bookingCustomer.getPostalCode());
            this.customer.setCity(bookingCustomer.getCity());
            this.customer.setCountry(bookingCustomer.getCountry());
        }

        // Set reservation details with null checks
        this.reservation = new ReservationDetails();
        this.reservation.setJettyLocation(booking.getJettyLocation());
        this.reservation.setNumberOfPassengers(booking.getNumberOfPassengers());
        this.reservation.setBookingDate(booking.getBookingDate());
        
        Package bookingPackage = booking.getSelectedPackage();
        if (bookingPackage != null) {
            this.reservation.setPackageType(bookingPackage.getPackageType());
            PackageDetails details = new PackageDetails();
            details.setId(bookingPackage.getId());
            details.setName(bookingPackage.getName());
            details.setDescription(bookingPackage.getDescription());
            details.setType(bookingPackage.getPackageType());
            details.setPrice(bookingPackage.getPrice());
            this.reservation.setPackageDetails(details);
        }
        
        if (booking.getAddOns() != null) {
            this.reservation.setAddOns(new HashSet<>(booking.getAddOns()));
        }

        // Set other options
        this.otherOptions = new OtherOptions();
        this.otherOptions.setAlternativeDate1(booking.getAlternativeDate1());
        this.otherOptions.setAlternativeDate2(booking.getAlternativeDate2());
        this.otherOptions.setRemarks(booking.getSpecialRequests());
    }
} 