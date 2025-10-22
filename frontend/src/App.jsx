import { useState } from 'react';
import Layout from './components/Layout';
import Dashboard from './components/Dashboard';
import StudentList from './components/StudentList';
import StudentForm from './components/StudentForm';
import AttendanceTracker from './components/AttendanceTracker';
import PerformanceReport from './components/PerformanceReport';

function App() {
  const [currentPage, setCurrentPage] = useState('dashboard');
  const [studentId, setStudentId] = useState(null);
  const [formMode, setFormMode] = useState('create'); // 'create' or 'edit'

  const navigateTo = (page, id = null, mode = 'create') => {
    setCurrentPage(page);
    setStudentId(id);
    setFormMode(mode);
  };

  const renderPage = () => {
    switch (currentPage) {
      case 'dashboard':
        return <Dashboard onNavigate={handleNavigation} />;
      case 'students':
        return <StudentList onEdit={(id) => navigateTo('student-form', id, 'edit')} onNavigate={handleLocationChange} />;
      case 'student-form':
        return (
          <StudentForm
            studentId={studentId}
            onSave={() => navigateTo('students')}
            onCancel={() => navigateTo('students')}
          />
        );
      case 'attendance':
        return <AttendanceTracker />;
      case 'performance':
        return <PerformanceReport />;
      default:
        return <Dashboard />;
    }
  };

  // Custom navigation handler for the Layout component
  const handleNavigation = (path) => {
    switch (path) {
      case '/':
        navigateTo('dashboard');
        break;
      case '/students':
        navigateTo('students');
        break;
      case '/attendance':
        navigateTo('attendance');
        break;
      case '/performance':
        navigateTo('performance');
        break;
      default:
        navigateTo('dashboard');
    }
  };

  // Custom navigation handler for components that use window.location.href
  const handleLocationChange = (value) => {
    if (typeof value === 'string') {
      if (value.includes('/students/new')) {
        navigateTo('student-form');
      } else if (value.includes('/students/') && value.includes('/edit')) {
        const id = value.split('/students/')[1].split('/edit')[0];
        navigateTo('student-form', id, 'edit');
      } else if (value.includes('/students')) {
        navigateTo('students');
      } else if (value.includes('/attendance')) {
        navigateTo('attendance');
      } else if (value.includes('/performance')) {
        navigateTo('performance');
      } else {
        navigateTo('dashboard');
      }
    }
  };

  return (
    <Layout onNavigate={handleNavigation}>
      {renderPage()}
    </Layout>
  );
}

export default App;
