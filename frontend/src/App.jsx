import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Sidebar from './components/Sidebar'
import TopBar from './components/TopBar'
import Dashboard from './components/Dashboard'

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen flex font-lato-regular rounded-2xl">
        <Sidebar />
        <div className="flex-1 flex flex-col">
          <TopBar />
          <main className="p-6">
            <Routes>
              <Route path="/" element={<Dashboard />} />
              {/* Add additional routes here as pages are built */}
            </Routes>
          </main>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default App