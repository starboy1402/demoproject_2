# DigiGov Setup Guide

## 🐛 Bugs Found and Fixed

### Bug #1: Security Vulnerability - Plain Text Password Storage
**Severity:** 🔴 CRITICAL
**Problem:** Passwords were stored in plain text in the database, making them vulnerable to data breaches.
**Files Modified:**
- `backend1/backend/pom.xml` - Added Spring Security Crypto dependency
- `backend1/backend/src/main/java/com/digigov/backend/service/UserService.java` - Implemented BCrypt password hashing

**Fix Details:**
- Added BCryptPasswordEncoder for secure password hashing
- Updated registration to hash passwords before storing
- Updated authentication to compare hashed passwords
- Updated password update functionality

### Bug #2: CORS Configuration Issue
**Severity:** 🟡 MEDIUM
**Problem:** CORS configuration only allowed specific ports (5500), preventing frontend from connecting when served from different ports or file:// protocol.
**Files Modified:**
- `backend1/backend/src/main/java/com/digigov/backend/config/CorsConfig.java`

**Fix Details:**
- Changed from `allowedOrigins` to `allowedOriginPatterns` with wildcards
- Added support for file:// protocol
- Added OPTIONS method support
- Added maxAge for better caching

### Bug #3: Performance Issue - Multiple DOMContentLoaded Listeners
**Severity:** 🟡 MEDIUM  
**Problem:** JavaScript had 4 separate DOMContentLoaded event listeners, causing inefficient page loading and potential memory issues.
**Files Modified:**
- `js/script.js`

**Fix Details:**
- Consolidated all DOMContentLoaded listeners into a single one
- Created modular initialization functions
- Added null checks to prevent errors when elements don't exist
- Improved overall page load performance

## 🚀 How to Start the Services

### 1. Start the Backend
```bash
cd /workspace/backend1/backend
./mvnw clean spring-boot:run
```

### 2. Start the Frontend
You can serve the frontend in several ways:

**Option A: Using Python (if available):**
```bash
cd /workspace
python3 -m http.server 5500
```

**Option B: Using Node.js (if available):**
```bash
cd /workspace
npx serve . -p 5500
```

**Option C: Open directly in browser:**
- Open `file:///workspace/index.html` in your browser

### 3. Test the Connection
- Open `/workspace/test-connection.html` in your browser
- Click "Run All Tests" to verify connectivity

## 🔧 Database Configuration

The backend is now configured to use H2 in-memory database for testing, which eliminates MySQL connection issues. 

**For Production:** Switch back to MySQL by updating `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/digigov_db
spring.datasource.username=root
spring.datasource.password=12345
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

## 📝 API Endpoints Working

- `GET /api/citizen/profile/check/{userId}` - Check if user has profile
- `POST /api/auth/register` - User registration with hashed passwords
- `POST /api/auth/login` - User login with password verification
- `POST /api/citizen/profile` - Create/update citizen profile
- `GET /api/citizen/profile/{userId}` - Get citizen profile

## ⚠️ Notes

1. **Security:** Passwords are now properly hashed using BCrypt
2. **CORS:** Frontend can connect from any localhost port or file protocol
3. **Performance:** Optimized JavaScript loading and DOM manipulation
4. **Database:** Using H2 for testing - switch to MySQL for production
5. **Validation:** Added proper null checks and error handling

## 🧪 Testing

Use the provided `test-connection.html` file to verify:
- Frontend can reach backend
- CORS is properly configured
- API endpoints are responding
- Authentication flow works

The backend should start on `http://localhost:8080` and be accessible from your frontend.