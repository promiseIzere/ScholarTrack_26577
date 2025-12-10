import React from 'react'
import { ClipboardList, PenSquare, Palette } from 'lucide-react'
import { Link } from 'react-router-dom';

function MyAssignments() {

    const assignments = [
        {
          id: 1,
          title: 'Computer Science',
          schedule: 'Today, 10:30 AM',
          marks: 70,
          totalMarks: 100,
          status: 'Completed',
          icon: <ClipboardList size={18} className="text-indigo-700" />,
          iconBg: 'bg-indigo-50',
        },
        {
          id: 2,
          title: 'Database Design',
          schedule: 'Tomorrow, 10:30 AM',
          marks: 70,
          totalMarks: 100,
          status: 'Completed',
          icon: <PenSquare size={18} className="text-purple-700" />,
          iconBg: 'bg-purple-50',
        },
        {
          id: 3,
          title: 'Principles of Accounting',
          schedule: '23 Feb, 12:30 PM',
          marks: 70,
          totalMarks: 100,
          status: 'Upcoming',
          icon: <Palette size={18} className="text-rose-700" />,
          iconBg: 'bg-rose-50',
        },
      ];

  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
        <div className="flex items-center mb-4 justify-between">
          <h2 className="text-lg font-semibold text-gray-900">My Assignments</h2>
          <Link to="/assignments" className="text-gray-500 bg-cyan-900 text-white px-4 py-2 rounded-md hover:bg-cyan-800 transition-colors">View All</Link>
        </div>
        <div className="divide-y divide-gray-100">
          <div className="grid grid-cols-3 items-center justify-center text-xs font-semibold text-gray-500 uppercase tracking-wide pb-3">
            <span className="text-left">Task</span>
            <span className="text-center">Grade</span>
            <span className="text-center">Update</span>
          </div>
          {assignments.map((assignment) => {
            const isCompleted = assignment.status === 'Completed'
            const badgeStyles = isCompleted
              ? 'text-purple-700 bg-purple-50 border border-purple-100'
              : 'text-amber-700 bg-amber-50 border border-amber-200'

            return (
              <div
                key={assignment.id}
                className="grid grid-cols-3 items-center py-4 gap-4"
              >
                <div className="flex items-center gap-3">
                  <div
                    className={`w-10 h-10 rounded-xl flex items-center justify-center ${assignment.iconBg}`}
                  >
                    {assignment.icon}
                  </div>
                  <div>
                    <p className="text-sm font-semibold text-gray-900">
                      {assignment.title}
                    </p>
                    <p className="text-xs text-gray-500">{assignment.schedule}</p>
                  </div>
                </div>
                <div className="flex flex-col items-center">
                  <p className="text-sm font-semibold text-gray-900 text-center">
                    {assignment.marks !== null
                      ? `${assignment.marks}/${assignment.totalMarks}`
                      : `--/${assignment.totalMarks}`}
                  </p>
                  <p className="text-xs text-gray-500 text-center">Final grade</p>
                </div>
                <div className="flex justify-center">
                  <span
                    className={`px-3 py-1 text-xs font-semibold rounded-full ${badgeStyles}`}
                  >
                    {assignment.status}
                  </span>
                </div>
              </div>
            )
          })}
        </div>
      </div>
  )
}

export default MyAssignments