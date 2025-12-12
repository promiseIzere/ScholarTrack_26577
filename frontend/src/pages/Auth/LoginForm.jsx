import React, { useState } from "react";
import InputFields from "../../components/ui/InputFields";
import logo from "../../assets/scolarTrack_logo.png";
import { User, Lock, LogIn, Eye, EyeOff } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import Button from "../../components/ui/Button";
import Radio from "../../components/ui/Radio";

function LoginForm({ setIsLogin, rememberMe, setRememberMe }) {
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();
  const handleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = () => {
    // need to send the login data to the backend
    navigate("/dashboard");
    console.log("Login");
  }

  return (
    <div className="flex flex-col justify-end py-24 px-10 bg-gray-100/90 backdrop-blur-sm w-full sm:w-1/2 ml-auto space-y-4">
      <img src={logo} alt="logo" className="w-1/2 h-1/2" />
      <div>
        <h1 className="text-xl font-bold">
          Welcome back! Please enter your details.
        </h1>
        <p className="text-sm text-gray-500">
          We are thrilled to have you back!
        </p>
      </div>

      <div className="grid grid-cols-2 border border-gray-300 rounded-full cursor-pointer">
        <button
          type="button"
          onClick={() => setIsLogin(true)}
          className="bg-cyan-900 text-white p-2 rounded-full w-full text-center transition-all"
        >
          Sign in
        </button>
        <button
        //   variant="primary"
          type="button"
          onClick={() => setIsLogin(false)}
          className="text-gray-700 p-2 rounded-full w-full text-center transition-all hover:bg-gray-200"
        >
          Sign up
        </button>
      </div>

      <br />
      <form className="flex flex-col gap-2">
        <InputFields
          className="rounded-full"
          icon={<User size={18} />}
          label="Username"
          type="text"
          placeholder="Enter your username"
        />
        
        <InputFields
          label="Password"
          icon={<Lock size={18} />}
          type={showPassword ? "text" : "password"}
          placeholder="Enter your password"
          rightIcon={
            <button
              type="button"
              onClick={handleShowPassword}
              className="text-gray-500 hover:text-gray-700 focus:outline-none"
            >
              {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
            </button>
          }
        />

        <div className="flex justify-between">
          <Radio
            label="Remember Me"
            name="remember"
            value="yes"
            checked={rememberMe === "yes"}
            onChange={() =>
              setRememberMe(rememberMe === "yes" ? "no" : "yes")
            }
          />
          <Link
            to="/resetPassword"
            className="text-sm text-gray-400 hover:text-gray-800"
          >
            Forgot password?
          </Link>
        </div>
        <br />
        <Button
          variant="primary"
          icon={<LogIn size={18} />}
          type="button"
          className="bg-cyan-900 w-full text-white p-2 rounded-full"
          onClick={handleLogin}
        >
          Login
        </Button>
      </form>
      <p className="text-sm text-gray-500">
        Don't have an account? <Link to="/signup" className="text-cyan-900 font-semibold hover:underline">Sign up</Link>
      </p>
      <hr className="border-gray-300" />
      <p className="text-sm text-gray-500">
        Or continue with
      </p>
      <Button
        variant="secondary"
        // icon={<Google size={18} />}
        type="button"
        className="bg-white w-full text-gray-700 p-2 rounded-full"
      >
        Google
      </Button>
    </div>
    
  );
}

export default LoginForm;