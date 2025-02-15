package com.rhumuda_new.rhumudasystem.controller;

import com.rhumuda_new.rhumudasystem.dto.ApiError;
import com.rhumuda_new.rhumudasystem.dto.BookingDTO;
import com.rhumuda_new.rhumudasystem.entity.AddOn;
import com.rhumuda_new.rhumudasystem.entity.Booking;
import com.rhumuda_new.rhumudasystem.entity.Booking.BookingStatus;
import com.rhumuda_new.rhumudasystem.repository.AddOnRepository;
import com.rhumuda_new.rhumudasystem.repository.BookingRepository;
import com.rhumuda_new.rhumudasystem.repository.JettyPointRepository;
import com.rhumuda_new.rhumudasystem.repository.PackageRepository;
import com.rhumuda_new.rhumudasystem.service.BookingService;
import com.rhumuda_new.rhumudasystem.service.EmailService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private JettyPointRepository jettyPointRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable String bookingId) {
        try {
            logger.info("Fetching booking with ID: {}", bookingId);
            
            if (bookingId == null || bookingId.trim().isEmpty()) {
                logger.error("Invalid booking ID provided: {}", bookingId);
                return ResponseEntity
                    .badRequest()
                    .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid booking ID", "Booking ID cannot be empty"));
            }

            var booking = bookingRepository.findByBookingId(bookingId.trim());
            
            if (booking.isEmpty()) {
                logger.error("Booking not found with ID: {}", bookingId);
                return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(HttpStatus.NOT_FOUND.value(), "Booking not found", "No booking found with ID: " + bookingId));
            }

            logger.info("Successfully retrieved booking with ID: {}", bookingId);
            return ResponseEntity.ok(booking.get());

        } catch (Exception e) {
            logger.error("Error fetching booking with ID: " + bookingId, e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal server error",
                    "An unexpected error occurred while fetching the booking: " + e.getMessage()
                ));
        }
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingDTO bookingDTO, BindingResult bindingResult) {
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
                return ResponseEntity
                    .badRequest()
                    .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors));
            }

            // Create new booking entity
            Booking booking = new Booking();

            // Set basic fields
            booking.setBookingId(bookingDTO.getBookingId());
            booking.setStatus(BookingStatus.valueOf(bookingDTO.getStatus()));
            booking.setFirstName(bookingDTO.getFirstName());
            booking.setLastName(bookingDTO.getLastName());
            booking.setPhoneNumber(bookingDTO.getPhoneNumber());
            booking.setEmail(bookingDTO.getEmail());
            booking.setAddressLine1(bookingDTO.getAddressLine1());
            booking.setAddressLine2(bookingDTO.getAddressLine2());
            booking.setPostalCode(bookingDTO.getPostalCode());
            booking.setCity(bookingDTO.getCity());
            booking.setCountry(bookingDTO.getCountry());

            // Set entity relationships
            booking.setJettyPoint(jettyPointRepository.findById(Long.parseLong(bookingDTO.getJettyPoint()))
                .orElseThrow(() -> new RuntimeException("JettyPoint not found")));

            booking.setPackageDetails(packageRepository.findById(Long.parseLong(bookingDTO.getPackageId()))
                .orElseThrow(() -> new RuntimeException("Package not found")));

            // Set dates and passengers
            booking.setBookingDate(bookingDTO.getBookingDate());
            booking.setPassengers(bookingDTO.getPassengers());
            booking.setAlternativeDate1(bookingDTO.getAlternativeDate1());
            booking.setAlternativeDate2(bookingDTO.getAlternativeDate2());
            booking.setSpecialRemarks(bookingDTO.getSpecialRemarks());

            // Set system fields
            booking.setCreatedAt(LocalDateTime.now());
            booking.setUpdatedAt(LocalDateTime.now());

            // Handle add-ons if present
            if (bookingDTO.getAddOns() != null && !bookingDTO.getAddOns().isEmpty()) {
                Set<AddOn> addOns = new HashSet<>();
                for (String addOnId : bookingDTO.getAddOns()) {
                    AddOn addOn = addOnRepository.findById(Long.parseLong(addOnId))
                        .orElseThrow(() -> new RuntimeException("AddOn not found: " + addOnId));
                    addOns.add(addOn);
                }
                booking.setAddOns(addOns);
            }

            // Save the booking
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);

        } catch (NumberFormatException e) {
            return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid ID format", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Error creating booking", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error creating booking", e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", e.getMessage()));
        }
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable String bookingId, @Valid @RequestBody BookingDTO bookingDTO) {
        try {
            logger.info("Updating booking with ID: {}", bookingId);
            
            // Find existing booking
            Booking existingBooking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

            // Update fields
            existingBooking.setStatus(BookingStatus.valueOf(bookingDTO.getStatus()));
            existingBooking.setFirstName(bookingDTO.getFirstName());
            existingBooking.setLastName(bookingDTO.getLastName());
            existingBooking.setPhoneNumber(bookingDTO.getPhoneNumber());
            existingBooking.setEmail(bookingDTO.getEmail());
            existingBooking.setAddressLine1(bookingDTO.getAddressLine1());
            existingBooking.setAddressLine2(bookingDTO.getAddressLine2());
            existingBooking.setPostalCode(bookingDTO.getPostalCode());
            existingBooking.setCity(bookingDTO.getCity());
            existingBooking.setCountry(bookingDTO.getCountry());

            // Update relationships
            existingBooking.setJettyPoint(jettyPointRepository.findById(Long.parseLong(bookingDTO.getJettyPoint()))
                .orElseThrow(() -> new RuntimeException("JettyPoint not found")));

            existingBooking.setPackageDetails(packageRepository.findById(Long.parseLong(bookingDTO.getPackageId()))
                .orElseThrow(() -> new RuntimeException("Package not found")));

            // Update dates and passengers
            existingBooking.setBookingDate(bookingDTO.getBookingDate());
            existingBooking.setPassengers(bookingDTO.getPassengers());
            existingBooking.setAlternativeDate1(bookingDTO.getAlternativeDate1());
            existingBooking.setAlternativeDate2(bookingDTO.getAlternativeDate2());
            existingBooking.setSpecialRemarks(bookingDTO.getSpecialRemarks());

            // Update system fields
            existingBooking.setUpdatedAt(LocalDateTime.now());

            // Update add-ons
            if (bookingDTO.getAddOns() != null) {
                Set<AddOn> addOns = new HashSet<>();
                for (String addOnId : bookingDTO.getAddOns()) {
                    AddOn addOn = addOnRepository.findById(Long.parseLong(addOnId))
                        .orElseThrow(() -> new RuntimeException("AddOn not found: " + addOnId));
                    addOns.add(addOn);
                }
                existingBooking.setAddOns(addOns);
            }

            // Save the updated booking
            Booking updatedBooking = bookingRepository.save(existingBooking);
            logger.info("Successfully updated booking with ID: {}", bookingId);
            return ResponseEntity.ok(updatedBooking);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format while updating booking: {}", bookingId, e);
            return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid ID format", e.getMessage()));
        } catch (RuntimeException e) {
            logger.error("Error updating booking: {}", bookingId, e);
            return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Error updating booking", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error updating booking: {}", bookingId, e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", e.getMessage()));
        }
    }

    @PutMapping("/{bookingId}/submit")
    public ResponseEntity<?> submitBooking(@PathVariable String bookingId) {
        try {
            // Get booking data
            BookingDTO booking = bookingService.getBookingById(bookingId);
            
            // Update booking status to PENDING
            bookingService.updateBookingStatus(bookingId, "PENDING");
            
            // Send emails
            emailService.sendBookingEmails(booking);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to submit booking", e.getMessage()));
        }
    }
}