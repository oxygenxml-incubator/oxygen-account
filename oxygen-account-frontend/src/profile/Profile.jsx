import "../css/GlobalStyle.css";

import React from "react";
import AppHeader from "../header/AppHeader.jsx";
import ProfileCard from "./ProfileCard.jsx";
import { Grid } from "@mui/material";

export default function Profile() {
  return (
    /* Main Container */
    <Grid container direction="column">
      {/* Header Section */}
      <Grid item container>
        <AppHeader showLogoutButton = {true}/>
      </Grid>

      <Grid item container>
        <ProfileCard />
      </Grid>
    </Grid>
  );
};



