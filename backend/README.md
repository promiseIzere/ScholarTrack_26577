# 🎓 ScholarTrack - Student Performance Management System

> A comprehensive academic tracking system that monitors student attendance, manages assignment submissions, and provides auto-generated performance insights through interactive charts and analytics.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [API Documentation](#-api-documentation)
- [Database Schema](#-database-schema)
- [Usage Examples](#-usage-examples)
- [Performance Analytics](#-performance-analytics)
- [Contributing](#-contributing)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)
- [Contact](#-contact)

---

## 🌟 Overview

ScholarTrack is a modern academic management system designed to help educational institutions track and improve student performance. The system provides real-time insights into student attendance patterns, assignment submissions, and overall academic progress.

### Why ScholarTrack?

Traditional educational systems often struggle with:
- Manual attendance tracking prone to errors
- Delayed feedback on student performance
- Difficulty identifying at-risk students early
- Lack of data-driven insights for intervention

ScholarTrack solves these problems by automating data collection and providing instant analytics that help educators make informed decisions.

### Key Capabilities

- **Real-time Attendance Tracking**: Monitor student attendance with multiple status options (Present, Absent, Late, Excused)
- **Assignment Management**: Create, distribute, and grade assignments with detailed feedback
- **Performance Analytics**: Auto-generated charts and reports showing trends, risk levels, and progress
- **Multi-level Location Support**: Track students across hierarchical geographic locations
- **Role-based Access**: Separate interfaces and permissions for admins, teachers, and students

---

## ✨ Features

### 📊 For Administrators

- **User Management**: Create and manage accounts for teachers, students, and other admins
- **Course Management**: Set up courses, assign teachers, and manage enrollments
- **System-wide Analytics**: View performance metrics across all courses and students
- **Location Management**: Maintain hierarchical location data (Province → District → Sector → Cell → Village)

### 👨‍🏫 For Teachers

- **Course Management**: View assigned courses and enrolled students
- **Assignment Creation**: Create assignments with titles, descriptions, due dates, and max scores
- **Grade Management**: Record scores and provide detailed feedback on submissions
- **Attendance Tracking**: Mark daily attendance with status and optional remarks
- **Student Insights**: Access individual student performance reports and trends

### 👨‍🎓 For Students

- **Course Dashboard**: View enrolled courses and upcoming assignments
- **Performance Tracking**: See grades, feedback, and overall progress
- **Attendance History**: Review attendance records and patterns
- **Performance Reports**: Access personalized analytics and risk assessments

### 📈 Analytics & Reporting

- **Attendance Rate Calculation**: Automatically compute attendance percentages
- **Grade Trends**: Track performance changes over time
- **Risk Prediction**: Identify students at risk based on attendance and grades
- **Average Score Analysis**: Calculate and visualize average assignment scores
- **Term-based Comparisons**: Compare performance across different academic terms

---

<!-- ## 🛠 Tech Stack

### Backend
- **Java 17+** - Modern Java features and performance
- **Spring Boot 3.x** - Rapid application development framework
- **Spring Data JPA** - Database abstraction and ORM
- **Hibernate** - Object-relational mapping
- **PostgreSQL** - Primary database (also supports MySQL, H2)
- **Maven** - Dependency management and build tool

### Architecture
- **RESTful API** - Clean, stateless API design
- **MVC Pattern** - Separation of concerns
- **Repository Pattern** - Data access abstraction
- **Service Layer** - Business logic encapsulation
- **DTO Pattern** - Data transfer objects for API responses -->

<!-- ### Database Features
- **UUID Primary Keys** - Enhanced security and distributed system support
- **Lazy Loading** - Optimized performance for large datasets
- **Database Indexes** - Fast query performance on frequently accessed columns
- **Foreign Key Constraints** - Data integrity enforcement
- **Cascade Operations** - Automatic relationship management

--- -->

## 📁 Project Structure

```
scholartrack/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── scholartrack/
│   │   │           ├── model/               # Entity classes
│   │   │           │   ├── User.java
│   │   │           │   ├── Student.java
│   │   │           │   ├── Teacher.java
│   │   │           │   ├── Course.java
│   │   │           │   ├── StudentCourse.java
│   │   │           │   ├── Assignment.java
│   │   │           │   ├── Performance.java
│   │   │           │   ├── Attendance.java
│   │   │           │   ├── Location.java
│   │   │           │   └── PerformanceReport.java
│   │   │           │
│   │   │           ├── repository/          # Data access layer
│   │   │           │   ├── UserRepository.java
│   │   │           │   ├── StudentRepository.java
│   │   │           │   ├── TeacherRepository.java
│   │   │           │   ├── CourseRepository.java
│   │   │           │   ├── EnrollmentRepository.java
│   │   │           │   ├── AssignmentRepository.java
│   │   │           │   ├── PerformanceRepository.java
│   │   │           │   └── AttendanceRepository.java
│   │   │           │
│   │   │           ├── service/             # Business logic
│   │   │           │   ├── UserService.java
│   │   │           │   ├── StudentService.java
│   │   │           │   ├── TeacherService.java
│   │   │           │   ├── CourseService.java
│   │   │           │   ├── EnrollmentService.java
│   │   │           │   ├── AssignmentService.java
│   │   │           │   ├── PerformanceService.java
│   │   │           │   └── AttendanceService.java
│   │   │           │
│   │   │           ├── controller/          # REST endpoints
│   │   │           │   ├── UserController.java
│   │   │           │   ├── StudentController.java
│   │   │           │   ├── TeacherController.java
│   │   │           │   ├── CourseController.java
│   │   │           │   ├── EnrollmentController.java
│   │   │           │   ├── AssignmentController.java
│   │   │           │   ├── PerformanceController.java
│   │   │           │   └── AttendanceController.java
│   │   │           │
│   │   │          
│   │   │           └── exception/           # Custom exceptions
│   │   │               ├── ResourceNotFoundException.java
│   │   │               └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties       # Main configuration
│   │       ├── application-dev.properties   # Development config
│   │       └── application-prod.properties  # Production config
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── scholartrack/
│                   ├── service/              # Service tests
│                   ├── controller/           # Controller tests
│                   └── repository/           # Repository tests
│
├── docs/
│   ├── API.md                               # API documentation
│   ├── DATABASE.md                          # Database schema
│   └── SETUP.md                             # Setup guide
│
├── pom.xml                                  # Maven dependencies
├── README.md                                # This file
└── LICENSE                                  # License information
```


### Installation

 **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/scholartrack.git
   cd scholartrack
   ```



## 📡 API Documentation

### Base URL
```
http://localhost:9090/api
```

### Authentication
Currently, the API uses basic role-based access. JWT authentication is planned for future releases.

### Main Endpoints

#### 👥 Students

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/students` | Get all students |
| GET | `/students/{id}` | Get student by ID |
| GET | `/students/number/{studentNumber}` | Get student by student number |
| POST | `/students` | Create new student |
| PUT | `/students/{id}` | Update student |
| DELETE | `/students/{id}` | Delete student |

#### 📚 Courses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/courses` | Get all courses |
| GET | `/courses/{id}` | Get course by ID |
| GET | `/courses/code/{courseCode}` | Get course by course code |
| POST | `/courses` | Create new course |
| PUT | `/courses/{id}` | Update course |
| DELETE | `/courses/{id}` | Delete course |

#### 📝 Enrollments

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/enrollments` | Get all enrollments |
| GET | `/enrollments/student/{studentId}` | Get student's enrollments |
| GET | `/enrollments/course/{courseId}` | Get course enrollments |
| POST | `/enrollments/enroll` | Enroll single student in course |
| POST | `/enrollments/enroll/students-to-course` | Enroll multiple students in course |
| POST | `/enrollments/enroll/student-to-courses` | Enroll student in multiple courses |
| DELETE | `/enrollments/unenroll` | Unenroll student from course |
| GET | `/enrollments/course/{courseId}/count` | Get enrollment count for course |

#### 📋 Assignments

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/assignments` | Get all assignments |
| GET | `/assignments/{id}` | Get assignment by ID |
| GET | `/assignments/course/{courseId}` | Get assignments for course |
| POST | `/assignments` | Create new assignment |
| PUT | `/assignments/{id}` | Update assignment |
| DELETE | `/assignments/{id}` | Delete assignment |

#### ✅ Attendance

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/attendance` | Get all attendance records |
| GET | `/attendance/student/{studentId}` | Get student's attendance |
| GET | `/attendance/course/{courseId}` | Get course attendance |
| GET | `/attendance/date/{date}` | Get attendance by date |
| POST | `/attendance` | Record attendance |
| PUT | `/attendance/{id}` | Update attendance record |
| DELETE | `/attendance/{id}` | Delete attendance record |

#### 📊 Performance

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/performance` | Get all performance records |
| GET | `/performance/student/{studentId}` | Get student's performance |
| GET | `/performance/assignment/{assignmentId}` | Get assignment submissions |
| POST | `/performance` | Record performance/submission |
| PUT | `/performance/{id}` | Update performance record |
| GET | `/performance/report/{studentId}` | Get student performance report |



## 🗄 Database Schema

### Core Entities

#### Users
- Manages authentication and authorization
- Roles: ADMIN, TEACHER, STUDENT
- Links to Teacher entity via one-to-one relationship

#### Students
- Stores student information and enrollment details
- Links to Location for geographic tracking
- Has relationships with Courses, Performance, and Attendance

#### Teachers
- Stores instructor information
- Links to User for authentication
- Teaches multiple Courses

#### Courses
- Course catalog with codes and descriptions
- Assigned to one Teacher
- Contains multiple Assignments

#### Student Courses (Enrollments)
- Junction table linking Students to Courses
- Tracks enrollment dates
- Ensures one enrollment per student-course pair

#### Assignments
- Homework and projects within Courses
- Has due dates and maximum scores
- Linked to Performance records

#### Performance
- Student submissions and scores for Assignments
- Includes feedback and submission timestamps
- Used for grade calculations

#### Attendance
- Daily attendance tracking per Course
- Multiple status options (PRESENT, ABSENT, LATE, EXCUSED)
- Includes optional remarks

#### Location
- Hierarchical geographic structure
- Five levels: Province → District → Sector → Cell → Village
- Self-referencing for parent-child relationships




---

## 📊 Performance Analytics

### Auto-Generated Insights

ScholarTrack automatically calculates and provides:

#### Attendance Rate
```
Attendance Rate = (Present Days / Total Days) × 100%
```

#### Average Score
```
Average Score = Sum of All Scores / Number of Assignments
```

#### Risk Level Prediction
Based on attendance and grade patterns:
- **LOW**: Attendance > 90%, Average Score > 75%
- **MEDIUM**: Attendance 75-90%, Average Score 60-75%
- **HIGH**: Attendance < 75%, Average Score < 60%

#### Grade Trends
Tracks performance changes over academic terms:
```java
Map<String, Double> gradeTrendByTerm = {
  "Fall 2024": 82.5,
  "Spring 2025": 88.3
}
```





## 🗓 Roadmap

### Current Version (v1.0)
- ✅ Student management
- ✅ Course management
- ✅ Enrollment system
- ✅ Assignment tracking
- ✅ Attendance monitoring
- ✅ Performance analytics

### Upcoming Features (v1.1)
- 🔄 JWT Authentication
- 🔄 Advanced reporting dashboard
- 🔄 Export to PDF/Excel
- 🔄 Bulk data import

### Future Plans (v2.0)
- 📋 Parent portal
- 📋 Mobile app
- 📋 Real-time notifications
- 📋 Integration with LMS platforms
- 📋 AI-powered insights
- 📋 Multi-language support
