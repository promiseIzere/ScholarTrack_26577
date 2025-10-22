# ScholarTrack Frontend

A modern React frontend for the ScholarTrack student management system, built with Tailwind CSS.

## Features

- **Dashboard**: Overview of system status and quick actions
- **Student Management**: Create, read, update, and delete students
- **Attendance Tracking**: Mark and view student attendance records
- **Performance Reports**: View detailed performance analytics
- **Responsive Design**: Works on desktop and mobile devices

## Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Backend API running on `http://localhost:8080`

### Installation

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm run dev
```

3. Open your browser and navigate to `http://localhost:5173`

### Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## API Integration

The frontend communicates with the backend API through the `apiService` located in `src/services/api.js`. Make sure your backend is running on `http://localhost:8080` or update the `API_BASE_URL` in the service file.

## Project Structure

```
src/
├── components/          # React components
│   ├── Layout.jsx      # Main layout with navigation
│   ├── Dashboard.jsx   # Dashboard overview
│   ├── StudentList.jsx # Student management list
│   ├── StudentForm.jsx # Student create/edit form
│   ├── AttendanceTracker.jsx # Attendance management
│   └── PerformanceReport.jsx # Performance analytics
├── services/
│   └── api.js          # API service for backend communication
└── App.jsx             # Main application component
```

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## Technologies Used

- React 19
- Tailwind CSS
- Vite
- Fetch API for HTTP requests

## Backend API Endpoints

The frontend expects the following backend endpoints:

- `GET /api/students` - Get all students
- `POST /api/students` - Create new student
- `GET /api/students/{id}` - Get student by ID
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student
- `POST /api/attendance/{studentId}` - Mark attendance
- `GET /api/attendance/{studentId}` - Get attendance records
- `GET /api/performance/{studentId}` - Get performance report
- `GET /api/health` - Health check

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request