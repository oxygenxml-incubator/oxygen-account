import "../css/GlobalStyle.css";
import "../css/ProfileStyle.css";

import React from "react";
import AppHeader from "../util/AppHeader.jsx";
import ProfileCard from "./ProfileCard.jsx";
import { Grid } from "@mui/material";

/**
 * This component displays the user's profile information.
 *
 * @returns {JSX.Element} The JSX element representing the user profile page.
 */
export default function Profile() {
  return (
    /* Main Container */
    <Grid container direction="column">
      {/* Header Section */}
      <Grid item container>
        <AppHeader showLogoutButton = {true}/>
      </Grid>

      {/* User's profile information Section */}
      <Grid item container>
        <ProfileCard />
      </Grid>
    </Grid>
  );
};



