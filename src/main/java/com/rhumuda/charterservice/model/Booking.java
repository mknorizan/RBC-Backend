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
    
    @Column(unique = true)
    private String bookingId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package selectedPackage;
    
    @Column(name = "booking_date")
    private LocalDateTime bookingDate;
    
    @Column(name = "number_of_passengers")
    private int numberOfPassengers;
    
    @Column(name = "jetty_location")
    private String jettyLocation;
    
    @Column(name = "special_requests", columnDefinition = "text")
    private String specialRequests;
    
    @ElementCollection
    @CollectionTable(name = "booking_addons", 
        joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "addon")
    private Set<String> addOns;
    
    @Column(name = "alternative_date1")
    private LocalDateTime alternativeDate1;
    
    @Column(name = "alternative_date2")
    private LocalDateTime alternativeDate2;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
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