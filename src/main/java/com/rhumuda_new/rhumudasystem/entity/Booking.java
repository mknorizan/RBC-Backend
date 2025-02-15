package com.rhumuda_new.rhumudasystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String bookingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.INCOMPLETE;

    // Customer Info
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String addressLine1;
    private String addressLine2;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;

    // Reservation Details
    @ManyToOne
    @JoinColumn(name = "jetty_point_id", nullable = false)
    private JettyPoint jettyPoint;

    @Column(nullable = false)
    private LocalDate bookingDate;
    
    @Column(nullable = false)
    private Integer passengers;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package packageDetails;

    @ManyToMany
    @JoinTable(
        name = "booking_addons",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "addon_id")
    )
    private Set<AddOn> addOns;

    // Other Options
    private LocalDate alternativeDate1;
    private LocalDate alternativeDate2;
    private String specialRemarks;

    // System Fields
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum BookingStatus {
        INCOMPLETE, PENDING, COMPLETE, CANCELLED
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public JettyPoint getJettyPoint() {
        return jettyPoint;
    }

    public void setJettyPoint(JettyPoint jettyPoint) {
        this.jettyPoint = jettyPoint;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Package getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(Package packageDetails) {
        this.packageDetails = packageDetails;
    }

    public Set<AddOn> getAddOns() {
        return addOns;
    }

    public void setAddOns(Set<AddOn> addOns) {
        this.addOns = addOns;
    }

    public LocalDate getAlternativeDate1() {
        return alternativeDate1;
    }

    public void setAlternativeDate1(LocalDate alternativeDate1) {
        this.alternativeDate1 = alternativeDate1;
    }

    public LocalDate getAlternativeDate2() {
        return alternativeDate2;
    }

    public void setAlternativeDate2(LocalDate alternativeDate2) {
        this.alternativeDate2 = alternativeDate2;
    }

    public String getSpecialRemarks() {
        return specialRemarks;
    }

    public void setSpecialRemarks(String specialRemarks) {
        this.specialRemarks = specialRemarks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}