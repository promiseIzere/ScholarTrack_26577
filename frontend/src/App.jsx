import React from 'react'
import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom'
import Sidebar from './components/layout/Sidebar'
import TopBar from './components/layout/TopBar'
import Dashboard from './pages/Dashboard'
import LoginPage from './pages/Auth/LoginPage'
import ResetPasswordPage from './pages/Auth/ResetPasswordPage'
import CoursesPage from './pages/Courses/CoursesPage'
import SchedulePage from './pages/Schedule/SchedulePage'

function AppContent() {
  const location = useLocation()
  const isAuthPage = location.pathname === '/login' || location.pathname === '/resetPassword'

  const routes = (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/resetPassword" element={<ResetPasswordPage />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/courses" element={<CoursesPage />} />
      <Route path="/schedule" element={<SchedulePage />} />
      <Route path="/" element={<Dashboard />} />
    </Routes>
  )

  if (isAuthPage) {
    return routes
  }

  return (
    <div className="min-h-screen flex font-lato-regular rounded-2xl">
      <Sidebar />
      <div className="flex-1 flex flex-col">
        <TopBar />
        <main className="p-6">
          {routes}
        </main>
      </div>
    </div>
  )
}

function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  )
}

export default App