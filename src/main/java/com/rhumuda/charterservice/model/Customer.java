package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(name = "address_line1")
    private String addressLine1;
    
    @Column(name = "address_line2")
    private String addressLine2;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    private String city;
    private String country;
    
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Transient
    private String firstName;
    
    @Transient
    private String lastName;
    
    public void setName(String fullName) {
        this.name = fullName;
        String[] parts = fullName.split(" ", 2);
        this.firstName = parts[0];
        this.lastName = parts.length > 1 ? parts[1] : "";
    }
    
    public String getFirstName() {
        if (firstName == null && name != null) {
            String[] parts = name.split(" ", 2);
            return parts[0];
        }
        return firstName;
    }
    
    public String getLastName() {
        if (lastName == null && name != null) {
            String[] parts = name.split(" ", 2);
            return parts.length > 1 ? parts[1] : "";
        }
        return lastName;
    }
    
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