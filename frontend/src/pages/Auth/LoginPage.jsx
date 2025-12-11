import React, { useState } from "react";
import loginBg from "../../assets/login_bg.png";
import LoginForm from "./LoginForm";
import SignupForm from "./SignupForm";

function LoginPage() {
  const [isLogin, setIsLogin] = useState(true);
  const [rememberMe, setRememberMe] = useState("no");
  
  return (
    <div
      className="flex h-fit max-h-[90vh] w-full items-center justify-center rounded-lg overflow-hidden max-w-6xl mx-auto bg-cover bg-center bg-no-repeat m-4"
      style={{ backgroundImage: `url(${loginBg})` }}
    >
      {isLogin ? (
        <LoginForm
          setIsLogin={setIsLogin}
          rememberMe={rememberMe}
          setRememberMe={setRememberMe}
        />
      ) : (
        <SignupForm setIsLogin={setIsLogin} />
      )}
    </div>
  );
}

export default LoginPage;