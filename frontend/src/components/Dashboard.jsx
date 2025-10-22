import { useState, useEffect } from 'react';
import apiService from '../services/api';

const Dashboard = ({ onNavigate }) => {
  const [stats, setStats] = useState({
    totalStudents: 0,
    activeStudents: 0,
    inactiveStudents: 0,
    systemStatus: 'Unknown'
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      setLoading(true);
      
      // Load students
      const students = await apiService.getStudents();
      const activeStudents = students.filter(s => s.status === 'ACTIVE').length;
      const inactiveStudents = students.filter(s => s.status === 'INACTIVE').length;
      
      // Check system health
      let systemStatus = 'Unknown';
      try {
        await apiService.healthCheck();
        systemStatus = 'Healthy';
      } catch (err) {
        systemStatus = 'Unhealthy';
      }
      
      setStats({
        totalStudents: students.length,
        activeStudents,
        inactiveStudents,
        systemStatus
      });
      setError(null);
    } catch (err) {
      setError('Failed to load dashboard data');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="mt-1 text-sm text-gray-500">
          Welcome to ScholarTrack - Your comprehensive student management system
        </p>
      </div>

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-md p-4">
          <div className="flex">
            <div className="ml-3">
              <h3 className="text-sm font-medium text-red-800">Error</h3>
              <div className="mt-2 text-sm text-red-700">{error}</div>
              <div className="mt-4">
                <button
                  onClick={loadDashboardData}
                  className="bg-red-100 px-3 py-2 rounded-md text-sm font-medium text-red-800 hover:bg-red-200"
                >
                  Try Again
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Stats Grid */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <div className="w-8 h-8 bg-blue-500 rounded-md flex items-center justify-center">
                  <span className="text-white text-sm">👥</span>
                </div>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">
                    Total Students
                  </dt>
                  <dd className="text-lg font-medium text-gray-900">
                    {stats.totalStudents}
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <div className="w-8 h-8 bg-green-500 rounded-md flex items-center justify-center">
                  <span className="text-white text-sm">✅</span>
                </div>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">
                    Active Students
                  </dt>
                  <dd className="text-lg font-medium text-gray-900">
                    {stats.activeStudents}
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <div className="w-8 h-8 bg-red-500 rounded-md flex items-center justify-center">
                  <span className="text-white text-sm">❌</span>
                </div>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">
                    Inactive Students
                  </dt>
                  <dd className="text-lg font-medium text-gray-900">
                    {stats.inactiveStudents}
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow rounded-lg">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <div className={`w-8 h-8 rounded-md flex items-center justify-center ${
                  stats.systemStatus === 'Healthy' ? 'bg-green-500' : 'bg-red-500'
                }`}>
                  <span className="text-white text-sm">
                    {stats.systemStatus === 'Healthy' ? '💚' : '💔'}
                  </span>
                </div>
              </div>
              <div className="ml-5 w-0 flex-1">
                <dl>
                  <dt className="text-sm font-medium text-gray-500 truncate">
                    System Status
                  </dt>
                  <dd className={`text-lg font-medium ${
                    stats.systemStatus === 'Healthy' ? 'text-green-600' : 'text-red-600'
                  }`}>
                    {stats.systemStatus}
                  </dd>
                </dl>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="bg-white shadow sm:rounded-lg">
        <div className="px-4 py-5 sm:p-6">
          <h3 className="text-lg leading-6 font-medium text-gray-900 mb-4">
            Quick Actions
          </h3>
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
            <button
              onClick={() => onNavigate && onNavigate('/students/new')}
              className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 text-center"
            >
              Add Student
            </button>
            <button
              onClick={() => onNavigate && onNavigate('/students')}
              className="bg-gray-600 text-white px-4 py-2 rounded-md hover:bg-gray-700 text-center"
            >
              View Students
            </button>
            <button
              onClick={() => onNavigate && onNavigate('/attendance')}
              className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 text-center"
            >
              Mark Attendance
            </button>
            <button
              onClick={() => onNavigate && onNavigate('/performance')}
              className="bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700 text-center"
            >
              View Reports
            </button>
          </div>
        </div>
      </div>

      {/* Getting Started */}
      {stats.totalStudents === 0 && (
        <div className="bg-blue-50 border border-blue-200 rounded-md p-4">
          <div className="flex">
            <div className="flex-shrink-0">
              <span className="text-blue-400 text-lg">💡</span>
            </div>
            <div className="ml-3">
              <h3 className="text-sm font-medium text-blue-800">
                Getting Started
              </h3>
              <div className="mt-2 text-sm text-blue-700">
                <p>
                  Welcome to ScholarTrack! To get started, add your first student by clicking the "Add Student" button above.
                  Once you have students, you can track their attendance and view performance reports.
                </p>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
