package com.rhumuda_new.rhumudasystem.service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.rhumuda_new.rhumudasystem.dto.BookingDTO;
import com.rhumuda_new.rhumudasystem.repository.JettyPointRepository;
import com.rhumuda_new.rhumudasystem.entity.JettyPoint;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JettyPointRepository jettyPointRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${admin.email:admin@rhumudaboatcharter.com.my}")
    private String adminEmail;

    private String emailTemplate;

    @PostConstruct
    public void init() {
        logger.info("EmailService initialized with username: {}", fromEmail);

        // Load email template
        try {
            ClassPathResource resource = new ClassPathResource("templates/email/booking-confirmation.html");
            emailTemplate = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            logger.info("Email template loaded successfully");
        } catch (Exception e) {
            logger.error("Failed to load email template", e);
            emailTemplate = null;
        }
    }

    private String getJettyPointName(String jettyPointId) {
        try {
            Optional<JettyPoint> jettyPoint = jettyPointRepository.findById(Long.parseLong(jettyPointId));
            return jettyPoint.map(JettyPoint::getName).orElse(jettyPointId);
        } catch (NumberFormatException e) {
            logger.warn("Invalid jetty point ID format: {}", jettyPointId);
            return jettyPointId;
        }
    }

    public void sendBookingEmails(BookingDTO booking) throws MessagingException {
        sendCustomerEmail(booking);
        sendAdminEmail(booking);
    }

    private void sendCustomerEmail(BookingDTO booking) throws MessagingException {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setFrom(fromEmail);
            helper.setTo(booking.getEmail());
            helper.setSubject("Booking Inquiry Confirmation - Rhumuda Boat Charter");

            String content;
            if (emailTemplate != null) {
                content = emailTemplate
                        .replace("${bookingId}", booking.getBookingId())
                        .replace("${firstName}", booking.getFirstName())
                        .replace("${lastName}", booking.getLastName())
                        .replace("${email}", booking.getEmail())
                        .replace("${phoneNumber}", booking.getPhoneNumber())
                        .replace("${addressLine1}", booking.getAddressLine1())
                        .replace("${addressLine2}", booking.getAddressLine2() != null ? booking.getAddressLine2() : "")
                        .replace("${categoryName}", booking.getCategoryName() != null ? booking.getCategoryName() : "")
                        .replace("${packageName}", booking.getPackageName() != null ? booking.getPackageName() : "")
                        .replace("${jettyName}", getJettyPointName(booking.getJettyPoint()))
                        .replace("${bookingDate}", booking.getBookingDate().format(DATE_FORMATTER))
                        .replace("${passengers}", String.valueOf(booking.getPassengers()));

                logger.info("Email content prepared with template");
            } else {
                content = generatePlainTextEmail(booking);
                logger.warn("Using fallback plain text email template");
            }

            helper.setText(content, true);
        };

        try {
            mailSender.send(preparator);
            logger.info("Customer confirmation email sent to: {}", booking.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send customer email: {}", e.getMessage(), e);
            throw new MessagingException("Failed to send customer email", e);
        }
    }

    private String generatePlainTextEmail(BookingDTO booking) {
        return String.format(
                """
                        Booking ID: %s

                        Dear %s %s,

                        Thank you for your recent inquiry about a boat charter with Rhumuda Boat Charter.

                        We have received your request as below:

                        Customer Details:
                        Name: %s %s
                        Email: %s
                        Phone: %s
                        Address: %s %s

                        Reservation Details:
                        Category: %s
                        Package: %s
                        Jetty: %s
                        Booking Date: %s
                        Group Size: %d

                        We are currently reviewing your request and will be in touch shortly with a confirmation and further details.

                        We appreciate your interest in Rhumuda Boat Charter and look forward to providing you with a memorable boating experience.
                        """,
                booking.getBookingId(),
                booking.getFirstName(), booking.getLastName(),
                booking.getFirstName(), booking.getLastName(),
                booking.getEmail(),
                booking.getPhoneNumber(),
                booking.getAddressLine1(),
                booking.getAddressLine2() != null ? booking.getAddressLine2() : "",
                booking.getCategoryName() != null ? booking.getCategoryName() : "",
                booking.getPackageName() != null ? booking.getPackageName() : "",
                getJettyPointName(booking.getJettyPoint()),
                booking.getBookingDate().format(DATE_FORMATTER),
                booking.getPassengers());
    }

    private void sendAdminEmail(BookingDTO booking) throws MessagingException {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setFrom(fromEmail);
            helper.setTo(adminEmail);
            helper.setSubject("New Booking Inquiry - " + booking.getBookingId());

            String content = String.format("""
                    <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6;">
                    <h2>New Booking Inquiry Received</h2>
                    <p><strong>Booking ID:</strong> %s</p>
                    <h3>Customer Details</h3>
                    <p>
                        <strong>Name:</strong> %s %s<br>
                        <strong>Email:</strong> %s<br>
                        <strong>Phone:</strong> %s<br>
                        <strong>Address:</strong> %s %s
                    </p>
                    <h3>Reservation Details</h3>
                    <p>
                        <strong>Category:</strong> %s<br>
                        <strong>Package:</strong> %s<br>
                        <strong>Jetty:</strong> %s<br>
                        <strong>Booking Date:</strong> %s<br>
                        <strong>Group Size:</strong> %d
                    </p>
                    <p><strong>Please review and respond to the customer.</strong></p>
                    </body>
                    </html>
                    """,
                    booking.getBookingId(),
                    booking.getFirstName(), booking.getLastName(),
                    booking.getEmail(),
                    booking.getPhoneNumber(),
                    booking.getAddressLine1(),
                    booking.getAddressLine2() != null ? booking.getAddressLine2() : "",
                    booking.getCategoryName() != null ? booking.getCategoryName() : "",
                    booking.getPackageName() != null ? booking.getPackageName() : "",
                    getJettyPointName(booking.getJettyPoint()),
                    booking.getBookingDate().format(DATE_FORMATTER),
                    booking.getPassengers());

            helper.setText(content, true);
        };

        try {
            mailSender.send(preparator);
            logger.info("Admin notification email sent for booking: {}", booking.getBookingId());
        } catch (Exception e) {
            logger.error("Failed to send admin email: {}", e.getMessage(), e);
            throw new MessagingException("Failed to send admin email", e);
        }
    }
}