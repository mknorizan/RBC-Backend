package com.rhumuda.charterservice.service;

import com.rhumuda.charterservice.model.Booking;
import com.rhumuda.charterservice.model.Customer;
import com.rhumuda.charterservice.model.Package;
import com.rhumuda.charterservice.model.BookingStatus;
import com.rhumuda.charterservice.repository.BookingRepository;
import com.rhumuda.charterservice.repository.CustomerRepository;
import com.rhumuda.charterservice.repository.PackageRepository;
import com.rhumuda.charterservice.dto.BookingDTO;
import com.rhumuda.charterservice.dto.BookingResponse;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final PackageRepository packageRepository;

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            // Parse YYYY-MM-DD format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return date.atStartOfDay(); // Sets time to 00:00:00
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD but got: " + dateStr);
        }
    }

    public BookingResponse createBooking(BookingDTO bookingDTO) {
        try {
            log.info("Starting to create booking with data: {}", bookingDTO);
            
            // Find package
            log.debug("Finding package with ID: {}", bookingDTO.getPackageId());
            Package selectedPackage = packageRepository.findById(Long.parseLong(bookingDTO.getPackageId()))
                .orElseThrow(() -> new RuntimeException("Package not found with ID: " + bookingDTO.getPackageId()));
            log.debug("Found package: {}", selectedPackage);

            // Create and validate customer
            log.debug("Creating customer with name: {}", bookingDTO.getCustomerName());
            Customer customer = new Customer();
            customer.setName(bookingDTO.getCustomerName());
            customer.setEmail(bookingDTO.getCustomerEmail());
            customer.setPhone(bookingDTO.getCustomerPhone());
            
            if (bookingDTO.getCustomerAddress() != null) {
                customer.setAddressLine1(bookingDTO.getCustomerAddress().getAddressLine1());
                customer.setAddressLine2(bookingDTO.getCustomerAddress().getAddressLine2());
                customer.setPostalCode(bookingDTO.getCustomerAddress().getPostalCode());
                customer.setCity(bookingDTO.getCustomerAddress().getCity());
                customer.setCountry(bookingDTO.getCustomerAddress().getCountry());
            }
            
            log.debug("Saving customer");
            customer = customerRepository.save(customer);
            log.debug("Customer saved with ID: {}", customer.getId());
            
            // Create booking
            log.debug("Creating booking entity");
            Booking booking = new Booking();
            booking.setBookingId("BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            booking.setBookingDate(parseDate(bookingDTO.getBookingDate()));
            booking.setNumberOfPassengers(bookingDTO.getNumberOfPassengers());
            booking.setJettyLocation(bookingDTO.getJettyLocation());
            booking.setSpecialRequests(bookingDTO.getSpecialRequests());
            booking.setAddOns(bookingDTO.getAddOns());
            booking.setAlternativeDate1(parseDate(bookingDTO.getAlternativeDate1()));
            booking.setAlternativeDate2(parseDate(bookingDTO.getAlternativeDate2()));
            booking.setStatus(BookingStatus.PENDING);
            booking.setCustomer(customer);
            booking.setSelectedPackage(selectedPackage);
            
            log.debug("Saving booking");
            booking = bookingRepository.save(booking);
            log.debug("Booking saved with ID: {}", booking.getId());
            
            log.info("Booking created successfully with ID: {}", booking.getBookingId());
            return new BookingResponse(booking);
        } catch (Exception e) {
            log.error("Error creating booking: ", e);
            throw new RuntimeException("Failed to create booking: " + e.getMessage(), e);
        }
    }

    private void saveBookingAddons(Booking booking, Set<String> addOns) {
        // Implementation for saving add-ons
    }
} 