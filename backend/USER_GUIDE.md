# ScholarTrack - User Guide & API Documentation

## Overview
ScholarTrack is a comprehensive Student Progress and Attendance Analyzer built with Spring Boot. It provides REST APIs for managing students, courses, assignments, attendance, and performance tracking with detailed analytics.

## Table of Contents
1. [Getting Started](#getting-started)
2. [User Roles & Authentication](#user-roles--authentication)
3. [Core Features & User Flows](#core-features--user-flows)
4. [API Endpoints Reference](#api-endpoints-reference)
5. [Sample API Calls](#sample-api-calls)
6. [Analytics & Reporting](#analytics--reporting)
7. [Troubleshooting](#troubleshooting)

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd scholartrack/backend
   ```

2. **Database Setup**
   - Install PostgreSQL
   - Create a database named `scholartrack`
   - Update `src/main/resources/application.properties` with your database credentials

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Verify Installation**
   - Application runs on `http://localhost:8080`
   - Health check: `GET http://localhost:8080/api/health`

## User Roles & Authentication

### User Roles
- **ADMIN**: Full system access, user management
- **TEACHER**: Course management, grading, attendance tracking
- **STUDENT**: View grades, assignments, attendance records

### Authentication
Currently uses basic authentication (development mode):
```bash
POST /api/auth/login
Content-Type: application/x-www-form-urlencoded

username=your_username&password=your_password
```

## Core Features & User Flows

### 1. Student Management Flow

#### For Administrators:

**Step 1: Create Student**
```bash
POST /api/students
Content-Type: application/json

{
  "studentNumber": "STU001",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@university.edu",
  "gender": "Male",
  "dateOfBirth": "2000-01-15",
  "enrollmentDate": "2024-01-15",
  "status": "ACTIVE",
  "location": {
    "id": "location-uuid"
  }
}
```

**Step 2: View All Students**
```bash
GET /api/students
```

**Step 3: Update Student Information**
```bash
PUT /api/students/{student-id}
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@university.edu"
}
```

**Step 4: Get Student Details**
```bash
GET /api/students/{student-id}
```

### 2. Course Management Flow

#### For Teachers/Administrators:

**Step 1: Create Course**
```bash
POST /api/courses
Content-Type: application/json

{
  "courseCode": "CS101",
  "courseName": "Introduction to Computer Science",
  "description": "Basic concepts of computer science",
  "credits": 3,
  "instructor": {
    "id": "teacher-uuid"
  }
}
```

**Step 2: View All Courses**
```bash
GET /api/courses
```

**Step 3: Create Assignment**
```bash
POST /api/assignments
Content-Type: application/json

{
  "course": {
    "id": "course-uuid"
  },
  "title": "Midterm Exam",
  "description": "Comprehensive exam covering chapters 1-5",
  "dueDate": "2024-03-15",
  "maxScore": 100.00
}
```

### 3. Enrollment Management Flow

#### For Students/Administrators:

**Step 1: Enroll Student in Course**
```bash
POST /api/enrollments/enroll?studentId={student-uuid}&courseId={course-uuid}
```

**Step 2: View Student's Enrollments**
```bash
GET /api/enrollments/student/{student-id}
```

**Step 3: View Course Enrollments**
```bash
GET /api/enrollments/course/{course-id}
```

**Step 4: Unenroll Student**
```bash
DELETE /api/enrollments/unenroll?studentId={student-uuid}&courseId={course-uuid}
```

### 4. Assignment & Performance Tracking Flow

#### For Teachers:

**Step 1: Create Assignment**
```bash
POST /api/assignments
Content-Type: application/json

{
  "course": {
    "id": "course-uuid"
  },
  "title": "Programming Assignment 1",
  "description": "Implement a calculator program",
  "dueDate": "2024-02-28",
  "maxScore": 50.00
}
```

**Step 2: Grade Student Performance**
```bash
POST /api/performances
Content-Type: application/json

{
  "student": {
    "id": "student-uuid"
  },
  "assignment": {
    "id": "assignment-uuid"
  },
  "score": 45.50,
  "feedback": "Good implementation, minor syntax errors",
  "submittedAt": "2024-02-27T10:30:00"
}
```

**Step 3: View Student Performance**
```bash
GET /api/performances/student/{student-id}
```

**Step 4: Get Assignment Statistics**
```bash
GET /api/performances/assignment/{assignment-id}/average
GET /api/performances/assignment/{assignment-id}/count
```

### 5. Attendance Management Flow

#### For Teachers:

**Step 1: Record Attendance**
```bash
POST /api/attendance
Content-Type: application/json

{
  "student": {
    "id": "student-uuid"
  },
  "course": {
    "id": "course-uuid"
  },
  "attendanceDate": "2024-02-15",
  "status": "PRESENT"
}
```

**Step 2: View Student Attendance**
```bash
GET /api/attendance/student/{student-id}
```

**Step 3: Get Attendance Statistics**
```bash
GET /api/attendance/student/{student-id}/stats
GET /api/attendance/course/{course-id}/stats
```

**Step 4: Filter Attendance by Date Range**
```bash
GET /api/attendance/date-range?startDate=2024-01-01&endDate=2024-01-31
```

### 6. Analytics & Reporting Flow

#### For Teachers/Administrators:

**Step 1: Generate Student Performance Report**
```bash
GET /api/analytics/student/{student-id}/report
```

**Step 2: Generate Report with Date Range**
```bash
GET /api/analytics/student/{student-id}/report/date-range?startDate=2024-01-01&endDate=2024-03-31
```

**Step 3: Get Student Analytics**
```bash
GET /api/analytics/student/{student-id}/stats
```

**Step 4: Get Course Analytics**
```bash
GET /api/analytics/course/{course-id}/stats
```

## API Endpoints Reference

### Authentication
- `POST /api/auth/login` - User login

### Students
- `GET /api/students` - List all students
- `POST /api/students` - Create student
- `GET /api/students/{id}` - Get student by ID
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student
- `GET /api/students/locations` - Get available locations

### Courses
- `GET /api/courses` - List all courses
- `POST /api/courses` - Create course
- `GET /api/courses/{id}` - Get course by ID
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

### Assignments
- `GET /api/assignments` - List all assignments
- `POST /api/assignments` - Create assignment
- `GET /api/assignments/{id}` - Get assignment by ID
- `PUT /api/assignments/{id}` - Update assignment
- `DELETE /api/assignments/{id}` - Delete assignment
- `GET /api/assignments/course/{courseId}` - Get assignments by course
- `GET /api/assignments/overdue` - Get overdue assignments

### Enrollments
- `GET /api/enrollments` - List all enrollments
- `POST /api/enrollments` - Create enrollment
- `POST /api/enrollments/enroll` - Enroll student in course
- `GET /api/enrollments/student/{studentId}` - Get student enrollments
- `GET /api/enrollments/course/{courseId}` - Get course enrollments
- `DELETE /api/enrollments/unenroll` - Unenroll student

### Performance
- `GET /api/performances` - List all performances
- `POST /api/performances` - Create performance record
- `GET /api/performances/student/{studentId}` - Get student performances
- `GET /api/performances/assignment/{assignmentId}` - Get assignment performances
- `GET /api/performances/student/{studentId}/average` - Get student average score

### Attendance
- `GET /api/attendance` - List all attendance records
- `POST /api/attendance` - Create attendance record
- `GET /api/attendance/student/{studentId}` - Get student attendance
- `GET /api/attendance/course/{courseId}` - Get course attendance
- `GET /api/attendance/student/{studentId}/stats` - Get student attendance stats

### Analytics
- `GET /api/analytics/student/{studentId}/report` - Get student performance report
- `GET /api/analytics/student/{studentId}/stats` - Get student analytics
- `GET /api/analytics/course/{courseId}/stats` - Get course analytics

## Sample API Calls

### Complete Student Workflow Example

1. **Create a Student**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "studentNumber": "STU001",
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@university.edu",
    "gender": "Female",
    "dateOfBirth": "2001-05-20",
    "enrollmentDate": "2024-01-15",
    "status": "ACTIVE"
  }'
```

2. **Create a Course**
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "courseCode": "MATH101",
    "courseName": "Calculus I",
    "description": "Introduction to differential calculus",
    "credits": 4
  }'
```

3. **Enroll Student in Course**
```bash
curl -X POST "http://localhost:8080/api/enrollments/enroll?studentId=STUDENT_UUID&courseId=COURSE_UUID"
```

4. **Create Assignment**
```bash
curl -X POST http://localhost:8080/api/assignments \
  -H "Content-Type: application/json" \
  -d '{
    "course": {"id": "COURSE_UUID"},
    "title": "Derivatives Quiz",
    "description": "Quiz on basic derivative rules",
    "dueDate": "2024-03-01",
    "maxScore": 25.00
  }'
```

5. **Record Performance**
```bash
curl -X POST http://localhost:8080/api/performances \
  -H "Content-Type: application/json" \
  -d '{
    "student": {"id": "STUDENT_UUID"},
    "assignment": {"id": "ASSIGNMENT_UUID"},
    "score": 22.50,
    "feedback": "Excellent work! Minor calculation error in problem 3.",
    "submittedAt": "2024-02-28T14:30:00"
  }'
```

6. **Record Attendance**
```bash
curl -X POST http://localhost:8080/api/attendance \
  -H "Content-Type: application/json" \
  -d '{
    "student": {"id": "STUDENT_UUID"},
    "course": {"id": "COURSE_UUID"},
    "attendanceDate": "2024-02-15",
    "status": "PRESENT"
  }'
```

7. **Generate Performance Report**
```bash
curl -X GET http://localhost:8080/api/analytics/student/STUDENT_UUID/report
```

## Analytics & Reporting

### Student Performance Reports
The analytics system provides comprehensive reports including:
- Overall GPA and grade trends
- Assignment completion rates
- Attendance statistics
- Performance by course
- Historical progress tracking

### Course Analytics
- Class average scores
- Assignment difficulty analysis
- Student engagement metrics
- Attendance patterns

### Date Range Filtering
Most analytics endpoints support date range filtering:
```bash
GET /api/analytics/student/{studentId}/report/date-range?startDate=2024-01-01&endDate=2024-03-31
```

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify PostgreSQL is running
   - Check database credentials in `application.properties`
   - Ensure database `scholartrack` exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Or stop the process using port 8080

3. **CORS Issues**
   - All controllers have `@CrossOrigin(origins = "*")` enabled
   - For production, specify exact origins

4. **Validation Errors**
   - Check required fields in request bodies
   - Ensure proper data types (UUIDs, dates, etc.)

### Error Responses
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server-side error

### Logs
Check application logs for detailed error information:
```bash
tail -f logs/application.log
```

## Best Practices

1. **Data Validation**: Always validate input data before sending requests
2. **Error Handling**: Implement proper error handling in your client application
3. **Pagination**: For large datasets, implement pagination on the client side
4. **Caching**: Cache frequently accessed data like course lists
5. **Security**: In production, implement proper authentication and authorization

## Support

For technical support or feature requests, please contact the development team or create an issue in the project repository.

---

*This guide covers the core functionality of ScholarTrack. For advanced features and custom integrations, refer to the API documentation or contact the development team.*
