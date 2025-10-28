# TutorConnect - Project 2 MVP

A Java web application for connecting students with tutors, built as part of an OOP with Design Patterns course.

## 🚀 Features

- **User Registration & Authentication**: Students and tutors can create accounts
- **Profile Management**: Users can update their personal information
- **Booking System**: Students can request tutoring sessions, tutors can approve/reject
- **Password Reset**: Secure password reset functionality with SHA-256 tokens
- **Beautiful UI**: Modern, responsive design with gradient styling

## 🏗️ Architecture

- **Three-tier Architecture**: Presentation, Business Logic, and Data layers
- **Java Servlets**: Presentation layer using `javax.servlet.*` (Java EE)
- **Service Layer**: Business logic with proper separation of concerns
- **DAO Pattern**: Data access objects with JDBC implementation
- **MySQL Database**: Persistent storage with proper schema design

## 🛠️ Technology Stack

- **Backend**: Java 11, Servlets, JDBC
- **Database**: MySQL with connection pooling
- **Security**: BCrypt password hashing, SHA-256 tokens
- **Frontend**: HTML5, CSS3, JavaScript
- **Build Tool**: Maven
- **Server**: Apache Tomcat 9.0.104

## 📁 Project Structure

```
tutorconnect/
├── src/main/java/com/tutorconnect/
│   ├── servlets/          # Presentation Layer
│   ├── service/           # Business Logic Layer
│   ├── dao/               # Data Access Layer
│   │   └── impl/          # DAO Implementations
│   ├── model/             # Domain Classes
│   ├── filters/           # Web Filters
│   └── util/              # Utilities
├── src/main/webapp/
│   ├── WEB-INF/
│   │   ├── web.xml        # Deployment Descriptor
│   │   └── lib/           # Dependencies
│   ├── css/               # Stylesheets
│   └── *.html             # Static Pages
├── database/
│   └── DDL.sql            # Database Schema
└── target/
    └── tutorconnect.war   # Deployable WAR File
```

## 🚀 Quick Start

### Prerequisites
- Java 11+
- Apache Tomcat 9.0.104
- MySQL Database
- Maven 3.6+

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/vickypede/TutorConnect.git
   cd TutorConnect
   ```

2. **Set up the database**:
   - Create MySQL database: `tutorconnect`
   - Run the DDL script: `database/DDL.sql`
   - Update database credentials in `src/main/java/com/tutorconnect/util/JdbcUtil.java`

3. **Build the project**:
   ```bash
   mvn clean package
   ```

4. **Deploy to Tomcat 9**:
   ```bash
   copy target/tutorconnect.war C:\apache-tomcat-9.0.104\webapps\
   ```

5. **Start Tomcat 9**:
   ```bash
   cd C:\apache-tomcat-9.0.104\bin
   startup.bat
   ```

6. **Access the application**:
   - Open browser: `http://localhost:8080/tutorconnect/`

## 🧪 Testing

Run the test script to verify functionality:
```bash
test-script.bat
```

## 📋 Key Components

### Servlets
- `IndexServlet`: Home page with navigation
- `SignupServlet`: User registration
- `LoginServlet`: User authentication
- `ProfileServlet`: Profile management
- `BookingServlet`: Booking management
- `PasswordResetServlet`: Password reset functionality

### Services
- `AuthService`: Authentication and user management
- `BookingService`: Booking business logic

### Models
- `User`: User entity with roles (STUDENT, TUTOR, ADMIN)
- `Booking`: Tutoring session booking
- `PasswordReset`: Password reset token management

## 🔒 Security Features

- **Password Hashing**: BCrypt with salt rounds
- **Session Management**: Secure session handling
- **Authentication Filter**: Protects sensitive routes
- **Token Security**: SHA-256 hashed reset tokens with expiration

## 📝 Database Schema

The application uses three main tables:
- `users`: User accounts with roles
- `bookings`: Tutoring session requests
- `password_resets`: Secure password reset tokens

## 🎨 UI Features

- **Responsive Design**: Works on desktop and mobile
- **Modern Styling**: Gradient backgrounds and smooth animations
- **User-Friendly Forms**: Clear labels and validation
- **Status Indicators**: Color-coded booking statuses

## 📚 Course Context

This project demonstrates:
- Object-Oriented Programming principles
- Design Patterns (MVC, DAO, Service Layer)
- Three-tier web application architecture
- Java EE servlet technology
- Database integration with JDBC
- Security best practices

## 🤝 Contributing

This is a course project. For educational purposes only.

## 📄 License

Educational project - see course guidelines.

---

**Built with ❤️ for OOP with Design Patterns Course**
