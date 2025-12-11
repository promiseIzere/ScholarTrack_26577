import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import InputFields from "../../components/ui/InputFields";
import logo from "../../assets/scolarTrack_logo.png";
import loginBg from "../../assets/login_bg.png";
import { Mail, Lock, Eye, EyeOff, ArrowLeft, KeyRound } from "lucide-react";
import Button from "../../components/ui/Button";

function ResetPasswordPage() {
  const navigate = useNavigate();
  const [step, setStep] = useState(1); // 1: Email step, 2: Reset password step
  const [email, setEmail] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleShowConfirmPassword = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  const handleEmailSubmit = (e) => {
    e.preventDefault();
    // In a real app, you would send a reset link to the email
    // For now, we'll just move to the next step
    if (email) {
      setStep(2);
    }
  };

  const handlePasswordReset = (e) => {
    e.preventDefault();
    // need to send the new password to the backend
    if (newPassword && confirmPassword && newPassword === confirmPassword) {
      // need to send the new password to the backend
      navigate("/login");
    }
  };

  return (
    <div
      className="flex h-screen w-full items-center justify-center overflow-hidden bg-cover bg-center bg-no-repeat p-4"
      style={{ backgroundImage: `url(${loginBg})` }}
    >
      <div className="flex flex-col rounded-xl py-12 px-10 bg-gray-100/90 opacity-90 backdrop-blur-sm w-full sm:w-1/2 ml-auto space-y-4 max-h-[90vh] overflow-y-auto">
        <img src={logo} alt="logo" className="w-1/2 h-1/2" />

        {step === 1 ? (
          <>
          {/* use otp to send the reset link to the email */}
            <div>
              <h1 className="text-xl font-bold">Reset your password</h1>
              <p className="text-sm text-gray-500">
                Enter your email address and we'll send you a link to reset your password.
              </p>
            </div>

            <form onSubmit={handleEmailSubmit} className="flex flex-col gap-3">
              <InputFields
                icon={<Mail size={18} />}
                type="email"
                placeholder="Enter your email address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />

              <Button
                variant="primary"
                icon={<KeyRound size={18} />}
                type="submit"
                className="bg-cyan-900 w-full text-white p-2 rounded-full"
              >
                Send reset link
              </Button>
            </form>

            <div className="flex items-center justify-center gap-2 text-sm text-gray-500">
              <ArrowLeft size={16} />
              <Link to="/login" className="text-cyan-900 font-semibold hover:underline">
                Back to login
              </Link>
            </div>
          </>
        ) : (
          <>
            <div>
              <h1 className="text-xl font-bold">Create new password</h1>
              <p className="text-sm text-gray-500">
                Please enter your new password below.
              </p>
            </div>

            <form onSubmit={handlePasswordReset} className="flex flex-col gap-3">
              <InputFields
                icon={<Lock size={18} />}
                type={showPassword ? "text" : "password"}
                placeholder="New password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                required
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
                type={showConfirmPassword ? "text" : "password"}
                placeholder="Confirm new password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                rightIcon={
                  <button
                    type="button"
                    onClick={handleShowConfirmPassword}
                    className="text-gray-500 hover:text-gray-700 focus:outline-none"
                  >
                    {showConfirmPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                  </button>
                }
              />

              {confirmPassword && newPassword !== confirmPassword && (
                <p className="text-sm text-red-500">Passwords do not match</p>
              )}

              <Button
                variant="primary"
                icon={<KeyRound size={18} />}
                type="submit"
                className="bg-cyan-900 w-full text-white p-2 rounded-full"
                disabled={newPassword !== confirmPassword || !newPassword || !confirmPassword}
              >
                Reset password
              </Button>
            </form>

            <div className="flex items-center justify-center gap-2 text-sm text-gray-500">
              <button
                onClick={() => setStep(1)}
                className="text-cyan-900 font-semibold hover:underline flex items-center gap-1"
              >
                <ArrowLeft size={16} />
                Back
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default ResetPasswordPage;

