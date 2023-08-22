import "./GlobalStyle.css";

import React from "react";
import Header from "./Header.jsx";
import AuthCard from "./AuthCard.jsx";
import { Grid } from "@mui/material";


/**
 * Component responsible for user authentication and rendering of both login and registration forms.
 * @returns {JSX.Element} The JSX representation of the Authentication component.
 */
export default function AuthContainer() {
 
  return (
    /* Main Container */
    <Grid container direction="column">
      {/* Header Section */}
      <Grid item container>
        <Header />
      </Grid>

      {/* Authentication Forms Section */}
      <Grid item container>
        <AuthCard />
      </Grid>

    </Grid>
  );
};



