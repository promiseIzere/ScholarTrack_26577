import React from 'react'
import StatCard from '../components/StatCard'
import {
  Users,
  Book,
  GraduationCap,
  ChartLine,
  Clock3,
  Check,
  ClipboardList,
  PenSquare,
  Palette,
} from 'lucide-react'
import {
  Area,
  AreaChart,
  Bar,
  BarChart,
  CartesianGrid,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
  ComposedChart,
} from 'recharts'
import MyAssignments from '../components/MyAssignments'

function Dashboard() {
  const performanceData = [
    { month: 'Jan', score: 72 },
    { month: 'Feb', score: 78 },
    { month: 'Mar', score: 81 },
    { month: 'Apr', score: 79 },
    { month: 'May', score: 84 },
    { month: 'Jun', score: 88 },
  ]

  const performanceComparison = [
    { month: 'Jan', current: 42, previous: 55 },
    { month: 'Feb', current: 58, previous: 72 },
    { month: 'Mar', current: 64, previous: 60 },
    { month: 'Apr', current: 59, previous: 63 },
    { month: 'May', current: 71, previous: 66 },
    { month: 'Jun', current: 68, previous: 62 },
  ]

  const activelyHoursData = [
    { day: 'S', hours: 1.5, remaining: 6.5 },
    { day: 'M', hours: 5, remaining: 3 },
    { day: 'T', hours: 3, remaining: 5 },
    { day: 'W', hours: 4.5, remaining: 3.5 },
    { day: 'T', hours: 7, remaining: 1 },
    { day: 'F', hours: 3.5, remaining: 4.5 },
    { day: 'S', hours: 3.5, remaining: 4.5 },
  ]

  return (
    <div className="flex flex-col gap-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-lato-bold text-gray-900">Overview</h1>
        <span className="text-sm text-gray-500">Updated weekly</span>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          borderColor="border-blue-800"
          color="bg-blue-100"
          iconBg="bg-blue-800"
          title="Courses in Progress"
          value="18"
          icon={<ChartLine size={22} />}
        />
        <StatCard
          borderColor="border-green-800"
          color="bg-green-100"
          iconBg="bg-green-800"
          title="Courses Completed"
          value="23"
          icon={<Book size={22} />}
        />
        <StatCard
          borderColor="border-purple-800"
          color="bg-purple-100"
          iconBg="bg-purple-800"
          title="Overall Performance"
          value="88%"
          icon={<Users size={22} />}
        />
        <StatCard
          borderColor="border-amber-700"
          color="bg-amber-100"
          iconBg="bg-amber-700"
          title="Time Invested"
          value="4.0h avg/day"
          icon={<Clock3 size={22} />}
        />
      </div>

{/* this data is mocked for now but should come from the backend */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 lg:col-span-2">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-lg font-semibold text-gray-900">Actively Hours</h2>
            <div className="inline-flex items-center gap-2 bg-gray-100 text-gray-700 px-3 py-1.5 rounded-full text-sm cursor-pointer hover:bg-gray-200 transition-colors">
              Weekly <span className="text-xs">▼</span>
            </div>
          </div>
          <div className="flex gap-6">
            <div className="flex-1 h-64">
              <ResponsiveContainer width="100%" height="100%">
              <BarChart 
                data={activelyHoursData} 
                margin={{ top: 5, right: 5, left: 5, bottom: 5 }}
                barSize={12}
                barCategoryGap="5%"
              >
                <CartesianGrid strokeDasharray="1 1" stroke="#f3f4f6" />

                <XAxis 
                  dataKey="day" 
                  tick={{ fill: '#6b7280', fontSize: 12 }}
                  axisLine={false}
                  tickLine={false}
                />

                <YAxis 
                  tick={{ fill: '#6b7280', fontSize: 11 }}
                  domain={[0, 4]}
                  ticks={[0, 2, 4, 8]}
                  tickFormatter={(value) => value === 0 ? '0' : `${value}h`}
                  axisLine={false}
                  tickLine={false}
                />

                {/* <Tooltip 
                  contentStyle={{ 
                    borderRadius: 4, 
                    borderColor: '#e5e7eb',
                    backgroundColor: '#fff',
                    padding: '8px 12px'
                  }}
                  formatter={(value) => [`${value}h`, 'Hours']}
                /> */}

                <Bar 
                  dataKey="remaining" 
                  stackId="a"
                  fill="#e9d5ff" 
                />

                <Bar 
                  dataKey="hours" 
                  stackId="a"
                  fill="#3b82f6" 
                  radius={[6, 6, 0, 0]}
                />
              </BarChart>

              </ResponsiveContainer>
            </div>
            <div className="flex flex-col gap-6 justify-start pt-2">
              <div>
                <p className="text-gray-500 text-sm mb-1">Time spent</p>
                <div className="flex items-center gap-2">
                  <span className="text-2xl font-bold text-gray-900">28</span>
                  <span className="text-emerald-600 text-xs bg-emerald-50 px-2 py-0.5 rounded-md font-medium">85%</span>
                </div>
              </div>
              <div>
                <p className="text-gray-500 text-sm mb-1">Lessons taken</p>
                <div className="flex items-center gap-2">
                  <span className="text-2xl font-bold text-gray-900">60</span>
                  <span className="text-emerald-600 text-xs bg-emerald-50 px-2 py-0.5 rounded-md font-medium">79%</span>
                </div>
              </div>
              <div>
                <p className="text-gray-500 text-sm mb-1">Exam passed</p>
                <div className="flex items-center gap-2">
                  <span className="text-2xl font-bold text-gray-900">10</span>
                  <span className="text-emerald-600 text-xs bg-emerald-50 px-2 py-0.5 rounded-md font-medium">100%</span>
                </div>
              </div>
            </div>
          </div>
        </div>
{/* ALL THIS DATA IS MOCKED FOR NOW BUT SHOULD COME FROM THE BACKEND */}
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-5 flex flex-col justify-between">
          <div>
            <h2 className="text-lg font-semibold text-gray-900 mb-4">Performance</h2>
            <div className="h-48">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={performanceComparison} margin={{ left: -12, right: 0, top: 5, bottom: 5 }}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#f3f4f6" />
                  <XAxis dataKey="month" tick={{ fill: '#6b7280', fontSize: 12 }} axisLine={false} tickLine={false} />
                  <YAxis tick={{ fill: '#6b7280', fontSize: 12 }} axisLine={false} tickLine={false} domain={[0, 80]} />
                  <Tooltip contentStyle={{ borderRadius: 10, borderColor: '#e5e7eb' }} />
                  <Line type="monotone" dataKey="current" stroke="#3b82f6" strokeWidth={3} dot={{ r: 4, strokeWidth: 2, stroke: '#3b82f6', fill: '#fff' }} />
                  <Line type="monotone" dataKey="previous" stroke="#a5b4fc" strokeWidth={3} dot={false} />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </div>
          <div className="mt-2 flex items-center justify-center">
            <div className="flex items-center justify-center gap-2 text-center">
              <span className="text-3xl font-extrabold text-gray-900">40%</span>
              <span className="text-sm text-gray-600">Your productivity is 40 higher compared to last month</span>
            </div>
          </div>
        </div>
      </div>

      <MyAssignments />

    </div>
  )
}

export default Dashboard