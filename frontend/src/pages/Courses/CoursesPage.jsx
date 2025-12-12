import React, { useState } from "react";
import { BookOpen, Key, User, Clock, TrendingUp, Search, X } from "lucide-react";
import Button from "../../components/ui/Button";
import InputFields from "../../components/ui/InputFields";

function CoursesPage() {
  const [activeTab, setActiveTab] = useState("available"); // "available" or "enrolled"
  const [enrollmentKey, setEnrollmentKey] = useState("");
  const [showEnrollmentModal, setShowEnrollmentModal] = useState(false);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");

  // Mock data - replace with API calls
  const availableCourses = [
    {
      id: 1,
      courseCode: "CS101",
      courseName: "Introduction to Computer Science",
      description: "Learn the fundamentals of computer science and programming.",
      credits: 3,
      teacher: "Dr. John Smith",
      enrollmentKey: "CS101-2024",
    },
    {
      id: 2,
      courseCode: "MATH201",
      courseName: "Calculus I",
      description: "Differential and integral calculus for engineering students.",
      credits: 4,
      teacher: "Prof. Jane Doe",
      enrollmentKey: "MATH201-2024",
    },
    {
      id: 3,
      courseCode: "ENG102",
      courseName: "English Composition",
      description: "Develop writing skills and critical thinking through essays.",
      credits: 3,
      teacher: "Dr. Robert Johnson",
      enrollmentKey: "ENG102-2024",
    },
    {
      id: 4,
      courseCode: "PHYS101",
      courseName: "Physics Fundamentals",
      description: "Introduction to mechanics, thermodynamics, and waves.",
      credits: 4,
      teacher: "Prof. Sarah Williams",
      enrollmentKey: "PHYS101-2024",
    },
  ];

  const enrolledCourses = [
    {
      id: 1,
      courseCode: "CS101",
      courseName: "Introduction to Computer Science",
      description: "Learn the fundamentals of computer science and programming.",
      credits: 3,
      teacher: "Dr. John Smith",
      enrollmentDate: "2024-01-15",
      progress: 65,
      assignmentsCompleted: 8,
      assignmentsTotal: 12,
      averageScore: 85,
    },
    {
      id: 2,
      courseCode: "MATH201",
      courseName: "Calculus I",
      description: "Differential and integral calculus for engineering students.",
      credits: 4,
      teacher: "Prof. Jane Doe",
      enrollmentDate: "2024-01-20",
      progress: 45,
      assignmentsCompleted: 5,
      assignmentsTotal: 11,
      averageScore: 78,
    },
  ];

  const handleEnrollClick = (course) => {
    setSelectedCourse(course);
    setShowEnrollmentModal(true);
  };

  const handleEnroll = (e) => {
    e.preventDefault();
    // TODO: Call API to enroll with enrollment key
    if (enrollmentKey === selectedCourse?.enrollmentKey) {
      alert(`Successfully enrolled in ${selectedCourse.courseName}!`);
      setShowEnrollmentModal(false);
      setEnrollmentKey("");
      setSelectedCourse(null);
      // Refresh enrolled courses list
    } else {
      alert("Invalid enrollment key. Please check with your teacher.");
    }
  };

  const filteredAvailableCourses = availableCourses.filter((course) =>
    course.courseName.toLowerCase().includes(searchQuery.toLowerCase()) ||
    course.courseCode.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const filteredEnrolledCourses = enrolledCourses.filter((course) =>
    course.courseName.toLowerCase().includes(searchQuery.toLowerCase()) ||
    course.courseCode.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="flex flex-col gap-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-lato-bold text-gray-900">Courses</h1>
      </div>

      {/* Tabs */}
      <div className="flex gap-2 border-b border-gray-200">
        <button
          onClick={() => setActiveTab("available")}
          className={`px-6 py-3 font-medium transition-colors ${
            activeTab === "available"
              ? "text-cyan-900 border-b-2 border-cyan-900"
              : "text-gray-600 hover:text-gray-900"
          }`}
        >
          Available Courses
        </button>
        <button
          onClick={() => setActiveTab("enrolled")}
          className={`px-6 py-3 font-medium transition-colors ${
            activeTab === "enrolled"
              ? "text-cyan-900 border-b-2 border-cyan-900"
              : "text-gray-600 hover:text-gray-900"
          }`}
        >
          My Courses ({enrolledCourses.length})
        </button>
      </div>

      {/* Search Bar */}
      <div className="flex items-center gap-4">
        <div className="flex-1 relative">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
          <InputFields
            placeholder="Search courses..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="pl-10"
          />
        </div>
      </div>

      {/* Available Courses Tab */}
      {activeTab === "available" && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredAvailableCourses.map((course) => (
            <div
              key={course.id}
              className="bg-white flex flex-col justify-between rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow"
            >
              <div className="flex items-start justify-between mb-4">
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-2">
                    <BookOpen className="text-cyan-900" size={20} />
                    <span className="text-sm font-semibold text-cyan-900">{course.courseCode}</span>
                  </div>
                  <h3 className="text-lg font-semibold text-gray-900 mb-2">{course.courseName}</h3>
                  <p className="text-sm text-gray-600 mb-4 line-clamp-2">{course.description}</p>
                </div>
              </div>

              <div className="flex items-center gap-4 text-sm text-gray-600 mb-4">
                <div className="flex items-center gap-1">
                  <User size={16} />
                  <span>{course.teacher}</span>
                </div>
                <div className="flex items-center gap-1">
                  <Clock size={16} />
                  <span>{course.credits} Credits</span>
                </div>
              </div>

              <Button
                variant="primary"
                icon={<Key size={18} />}
                onClick={() => handleEnrollClick(course)}
                className="w-full"
              >
                Enroll with Key
              </Button>
            </div>
          ))}
        </div>
      )}

      {/* Enrolled Courses Tab - on click, the user should see the details for the course */}
      {activeTab === "enrolled" && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredEnrolledCourses.map((course) => (
            <div
              key={course.id}
              className="bg-white flex flex-col justify-between rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow"
            >
              <div className="flex items-start justify-between mb-4">
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-2">
                    <BookOpen className="text-cyan-900" size={20} />
                    <span className="text-sm font-semibold text-cyan-900">{course.courseCode}</span>
                  </div>
                  <h3 className="text-lg font-semibold text-gray-900 mb-2">{course.courseName}</h3>
                  <p className="text-sm text-gray-600 mb-4 line-clamp-2">{course.description}</p>
                </div>
              </div>

              <div className="flex items-center gap-4 text-sm text-gray-600 mb-4">
                <div className="flex items-center gap-1">
                  <User size={16} />
                  <span>{course.teacher}</span>
                </div>
                <div className="flex items-center gap-1">
                  <Clock size={16} />
                  <span>{course.credits} Credits</span>
                </div>
              </div>

              {/* Progress Section */}
              <div className="mb-4">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-sm font-medium text-gray-700">Progress</span>
                  <span className="text-sm font-semibold text-cyan-900">{course.progress}%</span>
                </div>
                <div className="w-full bg-gray-200 rounded-full h-2">
                  <div
                    className="bg-cyan-900 h-2 rounded-full transition-all"
                    style={{ width: `${course.progress}%` }}
                  ></div>
                </div>
              </div>

              {/* Stats */}
              <div className="grid grid-cols-2 gap-4 pt-4 border-t border-gray-100">
                <div>
                  <p className="text-xs text-gray-500 mb-1">Assignments</p>
                  <p className="text-sm font-semibold text-gray-900">
                    {course.assignmentsCompleted}/{course.assignmentsTotal}
                  </p>
                </div>
                <div>
                  <p className="text-xs text-gray-500 mb-1">Average Score</p>
                  <div className="flex items-center gap-1">
                    <TrendingUp size={14} className="text-green-600" />
                    <p className="text-sm font-semibold text-gray-900">{course.averageScore}%</p>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Empty State */}
      {activeTab === "available" && filteredAvailableCourses.length === 0 && (
        <div className="text-center py-12">
          <BookOpen className="mx-auto text-gray-400 mb-4" size={48} />
          <p className="text-gray-600">No available courses found.</p>
        </div>
      )}

      {activeTab === "enrolled" && filteredEnrolledCourses.length === 0 && (
        <div className="text-center py-12">
          <BookOpen className="mx-auto text-gray-400 mb-4" size={48} />
          <p className="text-gray-600">You haven't enrolled in any courses yet.</p>
          <p className="text-sm text-gray-500 mt-2">
            Browse available courses and enroll using the enrollment key from your teacher.
          </p>
        </div>
      )}

      {/* Enrollment Modal */}
      {showEnrollmentModal && selectedCourse && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl shadow-lg p-6 max-w-md w-full mx-4">
            <div className="flex items-center justify-between mb-4">
              <h2 className="text-xl font-semibold text-gray-900">Enroll in Course</h2>
              <button
                onClick={() => {
                  setShowEnrollmentModal(false);
                  setEnrollmentKey("");
                  setSelectedCourse(null);
                }}
                className="text-gray-400 hover:text-gray-600"
              >
                <X size={24} />
              </button>
            </div>

            <div className="mb-4">
              <p className="text-sm text-gray-600 mb-2">
                Enter the enrollment key provided by your teacher for:
              </p>
              <p className="font-semibold text-gray-900">{selectedCourse.courseName}</p>
              <p className="text-sm text-gray-500">{selectedCourse.courseCode}</p>
            </div>

            <form onSubmit={handleEnroll}>
              <div className="mb-4">
                <InputFields
                  icon={<Key size={18} />}
                  placeholder="Enter enrollment key"
                  value={enrollmentKey}
                  onChange={(e) => setEnrollmentKey(e.target.value)}
                  required
                />
              </div>

              <div className="flex gap-3">
                <Button
                  type="button"
                  variant="secondary"
                  onClick={() => {
                    setShowEnrollmentModal(false);
                    setEnrollmentKey("");
                    setSelectedCourse(null);
                  }}
                  className="flex-1"
                >
                  Cancel
                </Button>
                <Button type="submit" variant="primary" className="flex-1">
                  Enroll
                </Button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default CoursesPage;

