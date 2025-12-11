import React from "react";

function Radio({ label, name, value, checked, onChange }) {
  return (
    <label className="flex items-center space-x-2 cursor-pointer select-none">
      <input
        type="radio"
        name={name}
        value={value}
        checked={checked}
        onChange={onChange}
        className="w-4 h-4 accent-blue-600 cursor-pointer"
        onClick={() => onChange(!checked)}
      />
      <span className="text-sm text-gray-700">{label}</span>
    </label>
  );
}

export default Radio;
