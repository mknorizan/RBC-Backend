package com.rhumuda.charterservice.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    
    public void sendBookingConfirmation(String to, String bookingId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(to);
        helper.setSubject("Booking Confirmation - " + bookingId);
        helper.setText(createBookingConfirmationEmail(bookingId), true);
        
        mailSender.send(message);
    }
    
    private String createBookingConfirmationEmail(String bookingId) {
        return String.format("""
            <h1>Booking Confirmation</h1>
            <p>Thank you for your booking. Your booking ID is: <strong>%s</strong></p>
            <p>We will contact you shortly to confirm your booking details.</p>
            """, bookingId);
    }
} 