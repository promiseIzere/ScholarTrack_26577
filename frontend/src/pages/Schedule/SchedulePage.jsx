import React, { useState } from "react";
import { Calendar, Clock, Plus, X, ChevronLeft, ChevronRight, Trash2 } from "lucide-react";
import Button from "../../components/ui/Button";
import InputFields from "../../components/ui/InputFields";

function SchedulePage() {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [selectedDate, setSelectedDate] = useState(null);
  const [showScheduleModal, setShowScheduleModal] = useState(false);
  const [schedules, setSchedules] = useState([]);
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    date: "",
    time: "",
    type: "study",
  });

  // Get first day of month and number of days
  const getDaysInMonth = (date) => {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
  };

  const getFirstDayOfMonth = (date) => {
    return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
  };

  const monthNames = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];

  const dayNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

  const navigateMonth = (direction) => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() + direction, 1));
  };

  const handleDateClick = (day) => {
    const clickedDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    setSelectedDate(clickedDate);
    setFormData({
      ...formData,
      date: clickedDate.toISOString().split("T")[0],
    });
    setShowScheduleModal(true);
  };

  const handleScheduleSubmit = (e) => {
    e.preventDefault();
    if (formData.title && formData.date && formData.time) {
      const newSchedule = {
        id: Date.now(),
        ...formData,
        dateTime: new Date(`${formData.date}T${formData.time}`),
      };
      setSchedules([...schedules, newSchedule]);
      setShowScheduleModal(false);
      setFormData({
        title: "",
        description: "",
        date: "",
        time: "",
        type: "study",
      });
      setSelectedDate(null);
    }
  };

  const handleDeleteSchedule = (id) => {
    setSchedules(schedules.filter((schedule) => schedule.id !== id));
  };

  const getSchedulesForDate = (day) => {
    const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    return schedules.filter((schedule) => {
      const scheduleDate = new Date(schedule.date);
      return (
        scheduleDate.getDate() === date.getDate() &&
        scheduleDate.getMonth() === date.getMonth() &&
        scheduleDate.getFullYear() === date.getFullYear()
      );
    });
  };

  const isToday = (day) => {
    const today = new Date();
    return (
      day === today.getDate() &&
      currentDate.getMonth() === today.getMonth() &&
      currentDate.getFullYear() === today.getFullYear()
    );
  };

  const isPastDate = (day) => {
    const date = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return date < today;
  };

  const getTypeColor = (type) => {
    const colors = {
      study: "bg-blue-100 text-blue-800 border-blue-300",
      assignment: "bg-purple-100 text-purple-800 border-purple-300",
      exam: "bg-red-100 text-red-800 border-red-300",
      other: "bg-gray-100 text-gray-800 border-gray-300",
    };
    return colors[type] || colors.other;
  };

  const daysInMonth = getDaysInMonth(currentDate);
  const firstDay = getFirstDayOfMonth(currentDate);
  const days = [];

  // Add empty cells for days before the first day of the month
  for (let i = 0; i < firstDay; i++) {
    days.push(null);
  }

  // Add days of the month
  for (let day = 1; day <= daysInMonth; day++) {
    days.push(day);
  }

  // Get upcoming schedules for sidebar
  const upcomingSchedules = [...schedules]
    .sort((a, b) => new Date(`${a.date}T${a.time}`) - new Date(`${b.date}T${b.time}`))
    .filter((schedule) => {
      const scheduleDateTime = new Date(`${schedule.date}T${schedule.time}`);
      return scheduleDateTime >= new Date();
    })
    .slice(0, 5);

  return (
    <div className="flex flex-col gap-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-lato-bold text-gray-900">Schedule</h1>
        <Button
          variant="primary"
          icon={<Plus size={18} />}
          onClick={() => {
            setSelectedDate(new Date());
            setFormData({
              ...formData,
              date: new Date().toISOString().split("T")[0],
            });
            setShowScheduleModal(true);
          }}
        >
          New Schedule
        </Button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Calendar */}
        <div className="lg:col-span-2 bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          {/* Calendar Header */}
          <div className="flex items-center justify-between mb-6">
            <button
              onClick={() => navigateMonth(-1)}
              className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
            >
              <ChevronLeft size={20} />
            </button>
            <h2 className="text-xl font-semibold text-gray-900">
              {monthNames[currentDate.getMonth()]} {currentDate.getFullYear()}
            </h2>
            <button
              onClick={() => navigateMonth(1)}
              className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
            >
              <ChevronRight size={20} />
            </button>
          </div>

          {/* Day Names */}
          <div className="grid grid-cols-7 gap-2 mb-2">
            {dayNames.map((day) => (
              <div key={day} className="text-center text-sm font-semibold text-gray-600 py-2">
                {day}
              </div>
            ))}
          </div>

          {/* Calendar Grid */}
          <div className="grid grid-cols-7 gap-2">
            {days.map((day, index) => {
              if (day === null) {
                return <div key={`empty-${index}`} className="aspect-square" />;
              }

              const dateSchedules = getSchedulesForDate(day);
              const isPast = isPastDate(day);
              const isCurrentDay = isToday(day);

              return (
                <div
                  key={day}
                  onClick={() => !isPast && handleDateClick(day)}
                  className={`
                    aspect-square border rounded-lg p-2 cursor-pointer transition-all
                    ${isPast ? "bg-gray-50 text-gray-400 cursor-not-allowed" : "hover:bg-cyan-50 hover:border-cyan-300"}
                    ${isCurrentDay ? "border-cyan-900 border-2 bg-cyan-50" : "border-gray-200"}
                    ${dateSchedules.length > 0 ? "bg-blue-50 border-blue-300" : ""}
                  `}
                >
                  <div className="flex flex-col h-full">
                    <span
                      className={`text-sm font-medium mb-1 ${
                        isCurrentDay ? "text-cyan-900" : isPast ? "text-gray-400" : "text-gray-900"
                      }`}
                    >
                      {day}
                    </span>
                    <div className="flex-1 flex flex-col gap-1 overflow-hidden">
                      {dateSchedules.slice(0, 2).map((schedule) => (
                        <div
                          key={schedule.id}
                          className={`text-xs px-1 py-0.5 rounded border truncate ${getTypeColor(schedule.type)}`}
                          title={schedule.title}
                        >
                          {schedule.time.split(":")[0]}:{schedule.time.split(":")[1]} - {schedule.title}
                        </div>
                      ))}
                      {dateSchedules.length > 2 && (
                        <div className="text-xs text-gray-500 px-1">
                          +{dateSchedules.length - 2} more
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Upcoming Schedules Sidebar - these should be clickable and open a modal with the details of the schedule 
        this data should come from the backend, the student should see what the teacher posted*/}
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <h3 className="text-lg font-semibold text-gray-900 mb-4">Upcoming</h3>
          {upcomingSchedules.length > 0 ? (
            <div className="space-y-3">
              {upcomingSchedules.map((schedule) => {
                const scheduleDateTime = new Date(`${schedule.date}T${schedule.time}`);
                const isTodaySchedule =
                  scheduleDateTime.toDateString() === new Date().toDateString();

                return (
                  <div
                    key={schedule.id}
                    className={`p-3 rounded-lg border ${
                      isTodaySchedule
                        ? "bg-cyan-50 border-cyan-300"
                        : "bg-gray-50 border-gray-200"
                    }`}
                  >
                    <div className="flex items-start justify-between mb-2">
                      <div className="flex-1">
                        <h4 className="font-semibold text-gray-900 text-sm mb-1">
                          {schedule.title}
                        </h4>
                        <div className="flex items-center gap-2 text-xs text-gray-600">
                          <Calendar size={12} />
                          <span>
                            {scheduleDateTime.toLocaleDateString("en-US", {
                              month: "short",
                              day: "numeric",
                              year: "numeric",
                            })}
                          </span>
                          <Clock size={12} className="ml-2" />
                          <span>
                            {schedule.time.split(":")[0]}:{schedule.time.split(":")[1]}
                          </span>
                        </div>
                        {schedule.description && (
                          <p className="text-xs text-gray-600 mt-1 line-clamp-2">
                            {schedule.description}
                          </p>
                        )}
                      </div>
                      <button
                        onClick={() => handleDeleteSchedule(schedule.id)}
                        className="text-red-500 hover:text-red-700 p-1"
                        title="Delete"
                      >
                        <Trash2 size={14} />
                      </button>
                    </div>
                    <span
                      className={`inline-block text-xs px-2 py-0.5 rounded ${getTypeColor(
                        schedule.type
                      )}`}
                    >
                      {schedule.type.charAt(0).toUpperCase() + schedule.type.slice(1)}
                    </span>
                  </div>
                );
              })}
            </div>
          ) : (
            <div className="text-center py-8 text-gray-500">
              <Calendar className="mx-auto mb-2 text-gray-400" size={32} />
              <p className="text-sm">No upcoming schedules</p>
            </div>
          )}
        </div>
      </div>

      {/* Schedule Modal */}
      {showScheduleModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl shadow-lg p-6 max-w-md w-full mx-4">
            <div className="flex items-center justify-between mb-4">
              <h2 className="text-xl font-semibold text-gray-900">Schedule Event</h2>
              <button
                onClick={() => {
                  setShowScheduleModal(false);
                  setSelectedDate(null);
                  setFormData({
                    title: "",
                    description: "",
                    date: "",
                    time: "",
                    type: "study",
                  });
                }}
                className="text-gray-400 hover:text-gray-600"
              >
                <X size={24} />
              </button>
            </div>

            <form onSubmit={handleScheduleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Title *</label>
                <InputFields
                  placeholder="Enter event title"
                  value={formData.title}
                  onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
                <textarea
                  className="w-full border border-gray-300 rounded-lg px-3 py-2 outline-none focus:border-cyan-900 resize-none"
                  rows="3"
                  placeholder="Enter event description"
                  value={formData.description}
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                />
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Date *</label>
                  <InputFields
                    type="date"
                    value={formData.date}
                    onChange={(e) => setFormData({ ...formData, date: e.target.value })}
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Time *</label>
                  <InputFields
                    type="time"
                    value={formData.time}
                    onChange={(e) => setFormData({ ...formData, time: e.target.value })}
                    required
                  />
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Type</label>
                <select
                  className="w-full border border-gray-300 rounded-lg px-3 py-2 outline-none focus:border-cyan-900"
                  value={formData.type}
                  onChange={(e) => setFormData({ ...formData, type: e.target.value })}
                >
                  <option value="study">Study</option>
                  <option value="assignment">Assignment</option>
                  <option value="exam">Exam</option>
                  <option value="other">Other</option>
                </select>
              </div>

              <div className="flex gap-3 pt-2">
                <Button
                  type="button"
                  variant="secondary"
                  onClick={() => {
                    setShowScheduleModal(false);
                    setSelectedDate(null);
                    setFormData({
                      title: "",
                      description: "",
                      date: "",
                      time: "",
                      type: "study",
                    });
                  }}
                  className="flex-1"
                >
                  Cancel
                </Button>
                <Button type="submit" variant="primary" className="flex-1">
                  Schedule
                </Button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default SchedulePage;

