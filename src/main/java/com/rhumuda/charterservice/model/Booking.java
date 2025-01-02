package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookingId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package selectedPackage;
    
    private LocalDateTime bookingDate;
    private int numberOfPassengers;
    private String jettyLocation;
    private String specialRequests;
    
    @ElementCollection
    @CollectionTable(name = "booking_add_ons", 
        joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "addon")
    private Set<String> addOns;
    
    private LocalDateTime alternativeDate1;
    private LocalDateTime alternativeDate2;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 