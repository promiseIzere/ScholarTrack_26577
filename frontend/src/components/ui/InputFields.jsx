import React from 'react';

function InputFields({
  value,
  onChange,
  placeholder = 'Enter text...',
  type = 'text',
  className = '',
  icon = null,
  rightIcon = null,
  required = false,
  ...otherProps
}) {
  return (
    <div className={`flex items-center border border-gray-300 rounded-lg px-3 py-2 bg-white ${className}`}>
      {/* Left Icon */}
      {icon && <span className="mr-2 text-gray-400">{icon}</span>}
      
      <input
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        required={required}
        className="flex-1 outline-none text-gray-700 placeholder-gray-400 bg-transparent"
        {...otherProps}
      />

      {rightIcon && <div className="ml-2 flex items-center text-gray-400">{rightIcon}</div>}
    </div>
  );
}

export default InputFields;