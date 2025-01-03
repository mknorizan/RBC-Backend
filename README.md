# Rhumuda Boat Charter - Backend Documentation

## Overview

The backend system for Rhumuda Boat Charter is built with Spring Boot, handling boat charter bookings and package management. The system uses a discriminator-based inheritance model for different package types (Boat and Fishing packages) and manages bookings with customer information.

## Technical Stack

### Core Technologies
- Spring Boot 3.2.2
- Java 17
- MySQL 8.0
- JPA/Hibernate
- Lombok
- JavaMail

### Development Tools
- VS Code with Spring Boot extensions
- Maven 3.6+
- MySQL Workbench/phpMyAdmin

## Project Structure
```bash
src/main/java/com/rhumuda/charterservice/
├── controller/
│ ├── BookingController.java # Handles booking requests
│ └── PackageController.java # Manages package operations
├── model/
│ ├── Package.java # Abstract base class for packages
│ ├── BoatPackage.java # Boat-specific package
│ ├── FishingPackage.java # Fishing-specific package
│ ├── Booking.java # Booking entity
│ ├── Customer.java # Customer information
│ └── BookingStatus.java # Enum for booking states
├── repository/
│ ├── BookingRepository.java
│ └── PackageRepository.java
├── service/
│ ├── BookingService.java # Business logic for bookings
│ └── EmailService.java # Email notification handling
└── dto/
├── BookingDTO.java # Data transfer objects
├── BookingResponse.java
└── PackageDTO.java
```

## Entity Relationships

### Package Inheritance Structure
- Abstract `Package` class
  - Common fields: id, packageId, title, description, duration, capacity
  - Discriminator column: package_type
- `BoatPackage` extends Package
  - Additional fields: adultPrice, kidPrice, privateBoatPrice
- `FishingPackage` extends Package
  - Additional fields: priceMin, priceMax, distance, techniques

### Booking-Customer Relationship
- One-to-Many relationship
- Customer can have multiple bookings
- Cascade operations for customer creation

## Database Setup

1. Create database with correct encoding:
```bash
sql
CREATE DATABASE rhumuda_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

2. Important: If tables exist and need restructuring:
```bash
sql
-- Drop tables in correct order to handle foreign keys
DROP TABLE IF EXISTS booking_addons;
DROP TABLE IF EXISTS booking_sessions;
DROP TABLE IF EXISTS package_techniques;
DROP TABLE IF EXISTS package_services;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS packages;
```

## Development Setup

1. Required VS Code Extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Lombok Annotations Support

2. Application Properties Configuration:
```bash
properties
spring.application.name=charter-service
spring.datasource.url=jdbc:mysql://localhost:3306/rhumuda_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.web.cors.allowed-origins=http://localhost:5173
```

3. Build and Run:
```bash
mvn clean # Clean previous builds
mvn install # Install dependencies
mvn spring-boot:run # Run application
```

## Package Management

### Package Types and Services
1. Boat Packages
   - Return trip services
   - Equipment rental
   - Price structure: adult, kid, private boat

2. Fishing Packages
   - Distance-based pricing
   - Equipment and techniques
   - Price range structure

### Important Implementation Details
- Package inheritance using `@DiscriminatorColumn`
- Service lists stored in separate tables
- Price ranges for different package types

## Booking System

### Booking Flow
1. Receive booking DTO from frontend
2. Create customer entity
3. Generate unique booking ID
4. Save booking with initial PENDING status
5. Send email confirmation
6. Return booking response

### Critical Components
- Automatic booking ID generation
- Status tracking (PENDING/CONFIRMED/CANCELLED)
- Email notification system
- Alternative dates handling

## Common Issues and Solutions

1. Database Foreign Key Constraints
   - Solution: Drop tables in correct order
   - Use cascade operations carefully

2. Package Type Discrimination
   - Solution: Proper @DiscriminatorValue annotation
   - Correct inheritance mapping

3. Lombok Integration
   - Solution: Install Lombok plugin
   - Use @Getter/@Setter instead of @Data for entities

## Testing

1. Package Management:
```bash
curl -X POST http://localhost:8080/api/bookings -H "Content-Type: application/json" -d '{
"packageId": "BT001",
"bookingDate": "2024-01-20T10:00:00",
"numberOfPassengers": 4
// ... other fields
}'
```

## Monitoring and Debugging

### Logging Configuration
```bash
properties
logging.level.org.springframework.web=DEBUG
logging.level.com.rhumuda.charterservice=DEBUG
spring.jpa.show-sql=true
```

### Common Debug Points
1. Package inheritance mapping
2. Booking creation flow
3. Email service integration
4. Database constraints

## Current Limitations
- Manual booking confirmation process
- Basic email templates
- No payment integration
- Limited availability checking

## Future Improvements
- Automated booking confirmation
- Enhanced email templates
- Real-time availability
- Payment gateway integration

## Support
For technical support:
- Email: mknorizan.work@gmail.com
- Documentation: [Internal Wiki]