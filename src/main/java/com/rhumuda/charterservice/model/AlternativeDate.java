package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "alternative_dates")
public class AlternativeDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime alternativeDate;
    
    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking;
    
    // Default constructor
    public AlternativeDate() {}
    
    // Constructor for date string
    public AlternativeDate(String dateStr) {
        if (dateStr != null) {
            this.alternativeDate = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        }
    }
} 