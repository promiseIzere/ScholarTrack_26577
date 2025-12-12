import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { Clipboard, BriefcaseBusiness, FileChartColumn, ClipboardCheck, Calendar, Settings, LogOut } from 'lucide-react'

function Sidebar() {
  const [activePath, setActivePath] = useState('/');

  const navItems = [
    { name: 'Dashboard', path: '/', icon: <Clipboard strokeWidth={1.25} /> },
    { name: 'Courses', path: '/courses', icon: <BriefcaseBusiness strokeWidth={1} /> },
    { name: 'Assignments', path: '/assignments', icon: <FileChartColumn strokeWidth={1} /> },
    { name: 'Attendance', path: '/attendance', icon: <ClipboardCheck strokeWidth={1} /> },
    { name: 'Schedule', path: '/schedule', icon: <Calendar strokeWidth={1} /> },
    { name: 'Settings', path: '/settings', icon: <Settings strokeWidth={1} /> },
  ]

  return (
    <aside className="sticky top-0 h-screen bg-gray-100 w-56 flex flex-col px-3 py-8 rounder-l-sm border-r-1 border-gray-300 font-lato-regular shadow-lg">
      <nav className="flex flex-col justify-between h-full">
        <div className="flex flex-col gap-2">
        {navItems.map((item) => (
          <Link
            key={item.name}
            to={item.path}
            onClick={() => setActivePath(item.path)}
            className={`
              flex items-center gap-5 px-4 py-2 rounded-lg transition-colors
              ${activePath === item.path ? 'bg-cyan-900 text-gray-100' : 'text-gray-800 hover:bg-cyan-700 hover:text-white'}
            `}
          >
            {item.icon}
            <span className="font-lato-regular">{item.name}</span>
          </Link>
        ))}
        </div>
        <Link to="/login" className="flex items-center gap-5 px-4 py-2 rounded-lg transition-colors text-gray-800 hover:bg-cyan-700 hover:text-white">
          <LogOut strokeWidth={2} />
          <span className="font-lato-regular">Logout</span>
        </Link>
      </nav>
    </aside>
  )
}

export default Sidebar
