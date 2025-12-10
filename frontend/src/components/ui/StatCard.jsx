import React from 'react';

function StatCard({ title, value, icon, iconBg= 'bg-white', color = 'bg-blue-100', borderColor = 'border-blue-500' }) {
  return (
    <div className={`flex flex-col p-4 rounded-lg shadow-sm ${color} w-56 justify-between`}>
      <div className="flex w-full items-center gap-2 mb-2">
        <div className={`p-2 text-white rounded-lg ${borderColor} ${iconBg}`}>
          {icon}
        </div>
        <span className="font-bold text-gray-700">{title}</span>
      </div>
      <h2 className="text-2xl font-bold text-gray-900">{value}</h2>
      <div className={`h-1 w-12 mt-2 rounded ${borderColor}`}></div>
      <div className={`flex border-b-3 w-1/4 ${borderColor} mb-2`}></div>
    </div>
  );
}

export default StatCard;
