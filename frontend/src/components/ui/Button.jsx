import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

const Button = ({
  children,
  onClick,
  type = 'button',
  variant = 'primary', // primary, secondary, danger
  size = 'md',         // sm, md, lg
  disabled = false,
  className = '',
  icon = null,         // optional icon component
}) => {
  const baseStyles = 'flex items-center justify-center font-medium rounded transition-colors duration-200 focus:outline-none';

  const variants = {
    primary: 'bg-cyan-900 text-white hover:bg-cyan-600',
    secondary: 'bg-gray-200 text-gray-800 hover:bg-gray-300',
    danger: 'bg-red-500 text-white hover:bg-red-600',
  };

  const sizes = {
    sm: 'px-3 py-1 text-sm',
    md: 'px-4 py-2 text-base',
    lg: 'px-6 py-3 text-lg',
  };

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={classNames(baseStyles, variants[variant], sizes[size], disabled && 'opacity-50 cursor-not-allowed', className)}
    >
      {icon && <span className="mr-2">{icon}</span>}
      {children}
    </button>
  );
};

Button.propTypes = {
  children: PropTypes.node.isRequired,
  onClick: PropTypes.func,
  type: PropTypes.oneOf(['button', 'submit', 'reset']),
  variant: PropTypes.oneOf(['primary', 'secondary', 'danger']),
  size: PropTypes.oneOf(['sm', 'md', 'lg']),
  disabled: PropTypes.bool,
  className: PropTypes.string,
  icon: PropTypes.node,
};

export default Button;
