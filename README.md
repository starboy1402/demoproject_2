# DigiGov - Government Service Portal

## Overview
DigiGov is a comprehensive government service portal that allows citizens to apply for various government services, manage their profiles, and track application status. The system includes both user-facing and administrative interfaces.

## Issues Fixed

### 1. Profile Dependency Issue ✅
**Problem**: Users could select services and submit applications without completing their citizen profile.

**Solution**: 
- Modified `ApplicationService.submitApplication()` to enforce profile dependency
- Added proper validation in the backend to check if `CitizenProfile` exists before allowing service applications
- Enhanced error messages to guide users to complete their profile first

**Code Changes**:
```java
// In ApplicationService.java
public Application submitApplication(Long userId, Long serviceId) {
    // Check if citizen profile exists - this is the dependency check
    Optional<CitizenProfile> profileOpt = citizenProfileRepository.findByUser_UserId(userId);
    if (!profileOpt.isPresent()) {
        throw new IllegalArgumentException("Citizen profile not found. Please complete your profile before applying for services.");
    }
    // ... rest of the method
}
```

### 2. Dashboard Data Loading Issue ✅
**Problem**: User dashboard was not displaying applications and payments even though they existed in the system.

**Solution**:
- Improved API error handling in the dashboard
- Enhanced data loading functions with proper error messages
- Added fallback data display for demo purposes
- Fixed field mapping between backend and frontend

**Code Changes**:
```javascript
// In userdashboard.html
function loadUserApplications(userId) {
    fetch(`http://localhost:8080/api/application/user/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            return response.json();
        })
        .then(apps => {
            // Enhanced data display with fallbacks
            if (Array.isArray(apps) && apps.length > 0) {
                // Display real data
            } else {
                // Show appropriate message
            }
        })
        .catch(error => {
            console.error('Error loading applications:', error);
            // Show user-friendly error message
        });
}
```

### 3. Admin Portal Accessibility Issue ✅
**Problem**: Admin section was difficult to access and had authentication issues.

**Solution**:
- Added admin portal link to main navigation
- Created `DataInitializer` to automatically create default admin accounts
- Enhanced admin login with better error handling
- Added comprehensive admin testing functionality

**Code Changes**:
```java
// In DataInitializer.java
private void initializeAdminData() {
    if (adminRepository.count() == 0) {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setCreatedAt(LocalDateTime.now());
        adminRepository.save(admin);
        System.out.println("Default admin account created: admin/admin123");
    }
}
```

## System Features

### User Features
- **User Registration & Authentication**: Secure login with BCrypt password hashing
- **Citizen Profile Management**: Complete profile creation with personal information
- **Service Selection**: Access to government services (Birth Certificate, Marriage Certificate, etc.)
- **Application Submission**: Submit applications for selected services
- **Payment Processing**: Integrated payment system with multiple methods
- **Dashboard**: View application status and payment history

### Admin Features
- **User Management**: View and manage user accounts
- **Application Management**: Review and approve/reject applications
- **System Statistics**: Monitor system usage and performance
- **Payment Verification**: Verify and process payments

### Demo Data
The system comes with pre-configured demo data:
- **Demo User**: `demo@teacher.com` / `secure123`
- **Admin Account**: `admin` / `admin123`
- **Default Services**: Birth Certificate, Marriage Certificate, Death Certificate

## User Flow

### Complete User Journey
1. **User Registration** → Create account with email and password
2. **Profile Completion** → Fill in citizen profile details (NID, address, etc.)
3. **Service Selection** → Choose government service to apply for
4. **Application Submission** → Submit application with required documents
5. **Payment Processing** → Complete payment for service fee
6. **Status Tracking** → Monitor application status through dashboard

### Profile Dependency Flow
```
User Login → Check Profile → Profile Complete? → Allow Service Access
                ↓
            Profile Incomplete → Redirect to Profile Completion
```

## Technical Architecture

### Backend (Spring Boot)
- **Controllers**: RESTful API endpoints for all operations
- **Services**: Business logic and validation
- **Repositories**: Data access layer with JPA
- **Entities**: Domain models for users, profiles, applications, etc.

### Frontend
- **HTML5**: Semantic markup with responsive design
- **CSS3**: TailwindCSS for styling
- **JavaScript**: Modern ES6+ with async/await
- **Local Storage**: Session management and user state

### Database
- **JPA/Hibernate**: Object-relational mapping
- **H2/MySQL**: Database options for development/production
- **Auto-initialization**: Sample data creation on startup

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 14+ (for frontend development)

### Backend Setup
1. Navigate to `backend1/backend/`
2. Run `mvn spring-boot:run`
3. Backend will start on `http://localhost:8080`
4. Default admin account will be created automatically

### Frontend Setup
1. Open `index.html` in a web browser
2. Use the "Run Complete User Flow Demo" button to test the system
3. Navigate through different sections using the navigation menu

### Testing the System
1. **Quick Demo**: Click "Run Complete User Flow Demo" on the main page
2. **Step-by-Step**: Use individual test buttons for each component
3. **Manual Testing**: Navigate through the actual user flow manually

## API Endpoints

### Authentication
- `POST /api/user/login` - User login
- `POST /api/admin/login` - Admin login

### User Management
- `POST /api/user/register` - User registration
- `GET /api/user/{id}` - Get user details

### Profile Management
- `GET /api/citizen/profile/check/{userId}` - Check profile status
- `POST /api/citizen/profile` - Create/update profile
- `GET /api/citizen/profile/{userId}` - Get profile details

### Applications
- `POST /api/application/submit` - Submit application
- `GET /api/application/user/{userId}` - Get user applications
- `GET /api/application/all` - Get all applications (admin)

### Payments
- `POST /api/payment/create` - Create payment
- `GET /api/payment/user/{userId}` - Get user payments
- `PUT /api/payment/verify` - Verify payment

## Security Features

- **Password Hashing**: BCrypt encryption for user passwords
- **Session Management**: Secure session handling with localStorage
- **Input Validation**: Server-side validation for all inputs
- **CORS Configuration**: Proper cross-origin resource sharing setup
- **Admin Authentication**: Separate admin portal with secure access

## Troubleshooting

### Common Issues

1. **Profile Not Found Error**
   - Ensure user has completed citizen profile
   - Check if profile data exists in database
   - Verify user ID mapping

2. **Dashboard Not Loading Data**
   - Check backend API connectivity
   - Verify user authentication status
   - Check browser console for errors

3. **Admin Login Issues**
   - Verify admin credentials (admin/admin123)
   - Check if backend is running
   - Ensure DataInitializer has run

### Debug Mode
- Open browser developer tools
- Check console for error messages
- Use Network tab to monitor API calls
- Verify localStorage contents

## Future Enhancements

- **Real-time Notifications**: WebSocket integration for status updates
- **Document Upload**: File attachment system for applications
- **Advanced Search**: Enhanced search and filtering capabilities
- **Mobile App**: React Native mobile application
- **Analytics Dashboard**: Comprehensive system analytics
- **Multi-language Support**: Internationalization features

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For technical support or questions:
- Check the troubleshooting section
- Review API documentation
- Contact the development team
- Submit issues through the repository

---

**Note**: This system is designed for demonstration and educational purposes. For production use, additional security measures, error handling, and testing should be implemented.