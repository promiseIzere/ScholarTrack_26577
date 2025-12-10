import React from "react";
import { Bell, Search, User } from "lucide-react";
import { Link } from "react-router-dom";
import InputFields from "../ui/InputFields";

function TopBar() {
  return (
    <div className="  w-full flex items-center justify-between px-4 py-6 font-lato-regular">
      <div className="flex text-lg font-lato-bold flex-col">
        <h1 className="text-gray-900 text-2xl font-bold">Hello there</h1>
        <p className="text-cyan-900 font-lato-regular text-sm">Here is your dashboard John Doe</p>
      </div>
      <div className="flex items-center justfy-even gap-2">
        <InputFields placeholder="Search..." icon={<Search size={18} />} className="w-72" />
        <Link to="/notifications" className=" text-black px-4 py-2">
          <Bell strokeWidth={1.5} />
        </Link>
        <Link to="/profile" className=" text-black px-4 py-2">
          <User strokeWidth={1.5} />
        </Link>
      </div>
    </div>
  );
}

export default TopBar;
