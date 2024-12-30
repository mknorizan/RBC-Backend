# Rhumuda Boat Charter - Backend System

## Overview
Rhumuda Boat Charter System is a comprehensive web-based booking platform designed for managing boat charter inquiries. The Phase 1 system implements a full inquiry-to-notification workflow, handling customer booking requests and administrative notifications through an automated system.

## Technical Stack Details
- Spring Boot 3.2.2
- Java 17
- Maven for dependency management and build automation
- JPA/Hibernate for database operations
- Jackson for JSON processing
- JavaMail for email services

### Email Service
- Gmail SMTP
- Port: 587
- TLS enabled
- Supports HTML formatting

## Software Tools to Installed
1. Microsoft Visual Studio Code (VS Code) - https://code.visualstudio.com/download
2. Apache Maven. Please download a stable version - https://maven.apache.org/download.cgi

## Installation & Setup
1. Open Terminal window in VS Code
2. Ensure current directory is pointing to: ```RBC-Backend```
3. Ensure Maven is installed. Run ```which mvn```
4. Navigate to ```src/main/resources```
5. Configure ```application.properties``` file. Ensure that the configuration is as follow :-  
    ```
   spring.application.name=charter-service
   spring.datasource.url=jdbc:mysql://localhost:3306/rhumuda_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.web.cors.allowed-origins=http://localhost:5173
   spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
   spring.web.cors.allowed-headers=*
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-specific-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   admin.email=admin@example.com
   ```
6. Build the project: ```mvn clean install```
7. Run the application: ```mvn spring-boot:run```

### Database Setup
1. Create MySQL database:   ```sql
   CREATE DATABASE rhumuda_db
   CHARACTER SET utf8mb4
   COLLATE utf8mb4_unicode_ci;   ```
2. Tables will be automatically created by Hibernate

## API Endpoints
- POST `/api/bookings`
  - Creates new booking inquiry
  - Requires JSON body with booking details
  - Returns booking confirmation with ID

- POST `/api/bookings/{bookingId}/cancel`
  - Cancels existing booking
  - Requires booking ID and reason
  - Returns updated booking status

## Support
For technical support or inquiries:
- Email: mknorizan.work@gmail.com
- Documentation: [link to documentation]
- Issue tracking: [link to issue tracker]

## Backend Development Important Key
1. Backend server on port 8080
2. Database on default MySQL port 3306
3. Automatic table creation/updates via Hibernate

## Current Limitations (Pending Development)
- No user authentication
- No admin interface
- Manual communication post-inquiry
- No payment processing
- No real-time availability checking
- Limited to single timezone (UTC)