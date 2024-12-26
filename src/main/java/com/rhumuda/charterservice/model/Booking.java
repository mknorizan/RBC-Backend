package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String bookingId;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime primaryDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    private Integer adults;
    private Integer seniors;
    private Integer children;
    private Integer infants;
    private String additionalComments;
    private Boolean newsletterOptIn;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;
    
    private String cancellationReason;
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AlternativeDate> alternativeDates = new ArrayList<>();
    
    private String jetty;
    private String charterType;
    
    public void addAlternativeDate(AlternativeDate date) {
        alternativeDates.add(date);
        date.setBooking(this);
    }
} 