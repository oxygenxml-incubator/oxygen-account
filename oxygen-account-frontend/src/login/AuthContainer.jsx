import React from "react";
import LoginForm from "./LoginForm.jsx";
import RegistrationForm from "./RegistrationForm.jsx";
import Header from "./Header.jsx";

import { useState } from "react";

/**
 * Login component responsible for rendering the registration form.
 * @returns {JSX.Element} The JSX representation of the Login component.
 */
export default function AuthContainer() {
  const [showLoginForm, setShowLoginForm] = useState(true);

  const toggleForm = () => {
    setShowLoginForm(!showLoginForm);
  };

  return (
    <div>
      <Header />
      <div style={{ display: "flex", justifyContent: "center", alignItems: "center"}}>
        <div style={{ maxWidth: "550px", width: "100%" }}>
          {showLoginForm ? <LoginForm toggleForm={toggleForm} /> : <RegistrationForm toggleForm={toggleForm} />}
        </div>
      </div>
    </div>
  );
};



