import React, { useState } from "react";
import InputFields from "../../components/ui/InputFields";
import logo from "../../assets/scolarTrack_logo.png";
import { User, Lock, LogIn, Mail, Phone, Eye, EyeOff } from "lucide-react";
import Button from "../../components/ui/Button";

function SignupForm({ setIsLogin }) {
  const [showPassword, setShowPassword] = useState(false);

  const handleShowPassword = () => {
    setShowPassword(!showPassword);
  }
  return (
    <div className="flex flex-col py-12 px-10 bg-gray-100/90 backdrop-blur-sm w-full sm:w-1/2 ml-auto space-y-4 overflow-y-auto max-h-[90vh] h-full">
      <img src={logo} alt="logo" className="w-1/2 h-1/2" />
      <div>
        <h1 className="text-xl font-bold">Create your account</h1>
        <p className="text-sm text-gray-500">
          Join ScholarTrack to get started.
        </p>
      </div>

      <div className="grid grid-cols-2 border border-gray-300 rounded-full cursor-pointer">
        <button
          type="button"
          onClick={() => setIsLogin(true)}
          className="text-gray-700 p-2 rounded-full w-full text-center transition-all hover:bg-gray-200"
        >
          Sign in
        </button>
        <button
          type="button"
          onClick={() => setIsLogin(false)}
          className="bg-cyan-900 text-white p-2 rounded-full w-full text-center transition-all"
        >
          Sign up
        </button>
      </div>

      <form className="flex flex-col gap-3">
        <InputFields icon={<User size={18} />} placeholder="Full name" />
        <InputFields
          icon={<Mail size={18} />}
          type="email"
          placeholder="Email address"
        />
        <InputFields icon={<User size={18} />} placeholder="Username" />
        <InputFields icon={<Phone size={18} />} placeholder="Phone number" />
        <InputFields
          icon={<Lock size={18} />}
          type={showPassword ? "text" : "password"}
          placeholder="Password"
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
        <InputFields
          icon={<Lock size={18} />}
          type={showPassword ? "text" : "password"}
          showPassword={showPassword}
          handleShowPassword={handleShowPassword}
          placeholder="Confirm password"
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

        <Button
          variant="primary"
          icon={<LogIn size={18} />}
          type="button"
          className="bg-cyan-900 w-full text-white p-2 rounded-full"
        >
          Create account
        </Button>
      </form>

      <p className="text-sm text-gray-500 text-center mt-2">
        Already have an account?{" "}
        <button
          onClick={() => setIsLogin(true)}
          className="text-cyan-900 font-semibold hover:underline"
        >
          Sign in
        </button>
      </p>
    </div>
  );
}

export default SignupForm;

