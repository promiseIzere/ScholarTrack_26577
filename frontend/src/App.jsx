import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Sidebar from './components/layout/Sidebar'
import TopBar from './components/layout/TopBar'
import Dashboard from './pages/Dashboard'
import LoginPage from './pages/Auth/LoginPage'
import ResetPasswordPage from './pages/Auth/ResetPasswordPage'

function App() {
  return (
    <BrowserRouter>
      {/* <div className="min-h-screen flex font-lato-regular rounded-2xl"> */}
        {/* <Sidebar /> */}
        {/* <div className="flex-1 flex flex-col"> */}
          {/* <TopBar /> */}
          {/* <main className="p-6"> */}
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/resetPassword" element={<ResetPasswordPage />} />
            </Routes>
          {/* </main>
        </div>
      </div> */}
    </BrowserRouter>
  )
}

export default App