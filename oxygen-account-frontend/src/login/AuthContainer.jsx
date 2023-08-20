import "./GlobalStyle.css";

import React from "react";
import Header from "./Header.jsx";
import CardForm from "./CardForm.jsx";
import { Grid } from "@mui/material";


/**
 * Login component responsible for rendering the registration form.
 * @returns {JSX.Element} The JSX representation of the Login component.
 */
export default function AuthContainer() {
 
  return (
    <Grid container>
      <Grid item xs={12}>
        <Header />
      </Grid>

      <Grid item xs={12}>
        <CardForm />
      </Grid>

    </Grid>
  );
};



