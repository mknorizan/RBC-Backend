package com.rhumuda.charterservice.service;

import com.rhumuda.charterservice.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    public void sendCustomerConfirmation(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getEmail());
        message.setSubject("Booking Confirmation - " + booking.getBookingId());
        message.setText(createCustomerEmailContent(booking));
        mailSender.send(message);
    }

    public void sendAdminNotification(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("New Booking Notification - " + booking.getBookingId());
        message.setText(createAdminEmailContent(booking));
        mailSender.send(message);
    }

    private String createCustomerEmailContent(Booking booking) {
        return String.format("""
            Dear %s %s,
            
            Thank you for your charter inquiry. Your booking ID is: %s
            
            Primary Date: %s
            Duration: %d hours
            Number of Guests: Adults: %d, Seniors: %d, Children: %d, Infants: %d
            
            We will contact you shortly to confirm your booking.
            
            Best regards,
            Rhumuda Charter Team
            """,
            booking.getFirstName(),
            booking.getLastName(),
            booking.getBookingId(),
            booking.getPrimaryDate(),
            booking.getDuration(),
            booking.getAdults(),
            booking.getSeniors(),
            booking.getChildren(),
            booking.getInfants()
        );
    }

    private String createAdminEmailContent(Booking booking) {
        return String.format("""
            New booking received:
            
            Booking ID: %s
            Name: %s %s
            Email: %s
            Phone: %s
            Primary Date: %s
            Duration: %d hours
            Guests: Adults: %d, Seniors: %d, Children: %d, Infants: %d
            Additional Comments: %s
            """,
            booking.getBookingId(),
            booking.getFirstName(),
            booking.getLastName(),
            booking.getEmail(),
            booking.getPhone(),
            booking.getPrimaryDate(),
            booking.getDuration(),
            booking.getAdults(),
            booking.getSeniors(),
            booking.getChildren(),
            booking.getInfants(),
            booking.getAdditionalComments()
        );
    }
} 