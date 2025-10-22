import { useState, useEffect } from 'react';
import apiService from '../services/api';

const AttendanceTracker = () => {
  const [students, setStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState('');
  const [attendanceRecords, setAttendanceRecords] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [dateRange, setDateRange] = useState({
    start: new Date().toISOString().split('T')[0],
    end: new Date().toISOString().split('T')[0]
  });

  useEffect(() => {
    loadStudents();
  }, []);

  const loadStudents = async () => {
    try {
      setLoading(true);
      const data = await apiService.getStudents();
      setStudents(data);
      if (data.length > 0) {
        setSelectedStudent(data[0].id);
      }
    } catch (err) {
      setError('Failed to load students');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const loadAttendance = async () => {
    if (!selectedStudent) return;
    
    try {
      setLoading(true);
      const data = await apiService.getAttendance(selectedStudent, dateRange.start, dateRange.end);
      setAttendanceRecords(data);
      setError(null);
    } catch (err) {
      setError('Failed to load attendance records');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (selectedStudent) {
      loadAttendance();
    }
  }, [selectedStudent, dateRange]);

  const markAttendance = async (present, date = null, remarks = '') => {
    if (!selectedStudent) return;

    try {
      setLoading(true);
      await apiService.markAttendance(selectedStudent, present, date, remarks);
      await loadAttendance(); // Reload attendance records
      setError(null);
    } catch (err) {
      setError('Failed to mark attendance');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDateRangeChange = (field, value) => {
    setDateRange(prev => ({
      ...prev,
      [field]: value
    }));
  };

  if (loading && students.length === 0) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <h1 className="text-2xl font-bold text-gray-900">Attendance Tracker</h1>

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-md p-4">
          <div className="text-sm text-red-700">{error}</div>
        </div>
      )}

      {students.length === 0 ? (
        <div className="text-center py-12">
          <div className="text-gray-500 text-lg">No students found. Please add students first.</div>
        </div>
      ) : (
        <>
          {/* Student Selection */}
          <div className="bg-white shadow sm:rounded-lg p-6">
            <div className="grid grid-cols-1 gap-6 sm:grid-cols-3">
              <div>
                <label htmlFor="student" className="block text-sm font-medium text-gray-700">
                  Select Student
                </label>
                <select
                  id="student"
                  value={selectedStudent}
                  onChange={(e) => setSelectedStudent(e.target.value)}
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                >
                  {students.map((student) => (
                    <option key={student.id} value={student.id}>
                      {student.firstName} {student.lastName}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label htmlFor="startDate" className="block text-sm font-medium text-gray-700">
                  Start Date
                </label>
                <input
                  type="date"
                  id="startDate"
                  value={dateRange.start}
                  onChange={(e) => handleDateRangeChange('start', e.target.value)}
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                />
              </div>

              <div>
                <label htmlFor="endDate" className="block text-sm font-medium text-gray-700">
                  End Date
                </label>
                <input
                  type="date"
                  id="endDate"
                  value={dateRange.end}
                  onChange={(e) => handleDateRangeChange('end', e.target.value)}
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                />
              </div>
            </div>
          </div>

          {/* Quick Attendance Actions */}
          <div className="bg-white shadow sm:rounded-lg p-6">
            <h3 className="text-lg font-medium text-gray-900 mb-4">Quick Actions</h3>
            <div className="flex space-x-4">
              <button
                onClick={() => markAttendance(true)}
                disabled={loading}
                className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 disabled:opacity-50"
              >
                Mark Present (Today)
              </button>
              <button
                onClick={() => markAttendance(false)}
                disabled={loading}
                className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700 disabled:opacity-50"
              >
                Mark Absent (Today)
              </button>
            </div>
          </div>

          {/* Attendance Records */}
          <div className="bg-white shadow overflow-hidden sm:rounded-md">
            <div className="px-4 py-5 sm:px-6">
              <h3 className="text-lg leading-6 font-medium text-gray-900">
                Attendance Records
              </h3>
              <p className="mt-1 max-w-2xl text-sm text-gray-500">
                Attendance history for the selected period
              </p>
            </div>
            
            {loading ? (
              <div className="flex justify-center items-center py-8">
                <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
              </div>
            ) : attendanceRecords.length === 0 ? (
              <div className="text-center py-8 text-gray-500">
                No attendance records found for the selected period
              </div>
            ) : (
              <ul className="divide-y divide-gray-200">
                {attendanceRecords.map((record) => (
                  <li key={record.id} className="px-4 py-4">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center">
                        <div className={`h-3 w-3 rounded-full mr-3 ${
                          record.present ? 'bg-green-400' : 'bg-red-400'
                        }`} />
                        <div>
                          <div className="text-sm font-medium text-gray-900">
                            {new Date(record.date).toLocaleDateString()}
                          </div>
                          {record.remarks && (
                            <div className="text-sm text-gray-500">{record.remarks}</div>
                          )}
                        </div>
                      </div>
                      <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                        record.present 
                          ? 'bg-green-100 text-green-800' 
                          : 'bg-red-100 text-red-800'
                      }`}>
                        {record.present ? 'Present' : 'Absent'}
                      </span>
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </>
      )}
    </div>
  );
};

export default AttendanceTracker;
