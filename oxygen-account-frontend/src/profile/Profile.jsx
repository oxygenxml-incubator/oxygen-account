import "../css/GlobalStyle.css";
import "../css/ProfileStyle.css";

import React, { useState, useEffect } from "react";
import AppHeader from "../shared/AppHeader.jsx";
import ProfileCard from "./ProfileCard.jsx";
import { Grid, LinearProgress } from "@mui/material";
import UserContext from "./UserContext.jsx";

/**
 * This component displays the user's profile information.
 *
 * @returns {JSX.Element} The JSX element representing the user profile page.
 */
export default function Profile() {
  // State variable for holding user data
  const [currentUserData, setCurrentUserData] = useState(null);
  
  // State variable indicating when the request is in progress.
  const [isDataLoadingActive, setIsDataLoadingActive] = useState(true);

  /**
   * Get the current user's data.
   */
  const getUserCurrentData = () => {
    setIsDataLoadingActive(true);

    fetch('api/users/me', {
      method: 'GET'
    }).then((response) => {
      return response.json();
    }).then((data) => {
      setIsDataLoadingActive(false);

      if (data.errorMessage) {
        throw new Error(data.errorMessage);
      }

      setCurrentUserData(data);
    }).catch(error => {
      setIsDataLoadingActive(false);

      setSnackbarMessage(error.message);

      setShowSnackbar(true);
    });
  }

  /**
  * Fetch user data on component mount
  */
  useEffect(() => {
    getUserCurrentData();
  }, []);

  /**
   * Updates the current user data state with the response from server.
   */
  const updateCurrentUser = (updatedUser) => {
    setCurrentUserData({
      ...updatedUser,
    });
  }

  return (
    <UserContext.Provider value={{ currentUserData, updateCurrentUser }}>
      {/* Main Container */}
      <Grid container direction="column">
        {/* Header Section */}
        <Grid item container>
          <AppHeader showLogoutButton={true} />
        </Grid>

        {/* User's profile information Section */}
        {isDataLoadingActive ? (
          <Grid item xs>
            <LinearProgress />
          </Grid>
          ) : (        
          <Grid item container>
            <ProfileCard />
          </Grid>
          )}

      </Grid>
    </UserContext.Provider>
  );
};



