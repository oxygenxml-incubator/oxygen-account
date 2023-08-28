import React, { useState, useEffect } from 'react';
import { Grid, Card, CardHeader, CardContent, LinearProgress, Typography, Snackbar, Alert } from '@mui/material';

import OxygenAvatar from "../util/OxygenAvatar.jsx";

/**
 * Component responsible for rendering the profile card containing current user info.
 * 
 * @returns {JSX.Element} The JSX representation of the ProfileCard component.
 */
function ProfileCard() {
    // State variable for holding user data
    const [userData, setUserData] = useState(null);

    // State variable indicating when the request is in progress.
    const [isLoadingActive, setIsLoadingActive] = useState(true);

    // State variable for showing or hiding the Snackbar component.
    const [showSnackbar, setShowSnackbar] = useState(false);

    // State variable for storing the message to display in the Snackbar.
    const [snackbarMessage, setSnackbarMessage] = useState('');

    // Fetch user data on component mount
    useEffect(() => {
        setIsLoadingActive(true);

        fetch('api/users/me', {
            method: 'GET'
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                setUserData(data);

                setIsLoadingActive(false);
            })
            .catch(error => {
                setIsLoadingActive(false);

                setSnackbarMessage("Failed to display user data.");

                setShowSnackbar(true);
            });

    }, []);

    /*
     * Handle the Snackbar close event.
     */
    const handleSnackbarClose = () => {
        setShowSnackbar(false);
    };

    return (
        // Main container for the profile card
        <Grid container justifyContent="center" alignItems="center">
            <Grid item xs={12} md={10} lg={8} xl={6} style={{ marginTop: 50 }}>
                <Card>
                    {/* Header for displaying title*/}
                    <CardHeader
                        title={"Profile"}
                    />

                    {/* Display loading process or user information */}
                    <CardContent>
                        {isLoadingActive ? (
                            <LinearProgress />
                        ) : (
                            <Grid container direction="row" spacing={2}>
                                {/* Display user avatar */}
                                <Grid item style={{ maxWidth: 'fit-content' }}>
                                    <OxygenAvatar name={userData.name} />
                                </Grid>

                                {/* Display user info */}
                                <Grid item container style={{ maxWidth: 'fit-content' }}>
                                    <Grid item container spacing={1}>
                                        <Grid item>
                                            <Typography className="boldTextTypography">
                                                Name:
                                            </Typography>
                                        </Grid>
                                        <Grid item>
                                            <Typography className="grayColorTextTypography">
                                                {userData.name}
                                            </Typography>
                                        </Grid>
                                    </Grid>

                                    <Grid item container spacing={1}>
                                        <Grid item>
                                            <Typography className="boldTextTypography">
                                                Email:
                                            </Typography>
                                        </Grid>
                                        <Grid item>
                                            <Typography className="grayColorTextTypography">
                                                {userData.email}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>

                        )}
                    </CardContent>
                </Card>
            </Grid>

            {/* Conditionally render the Snackbar for showing error messages if showSnackbar is true */}
            {showSnackbar &&
                <Grid item>
                    <Snackbar
                        open={showSnackbar}
                        autoHideDuration={5000}
                        onClose={handleSnackbarClose}
                        anchorOrigin={{
                            vertical: "bottom",
                            horizontal: "center"
                        }}
                    >
                        <Alert
                            elevation={6}
                            variant="filled"
                            onClose={handleSnackbarClose}
                            severity="error"
                        >
                            {snackbarMessage}
                        </Alert>
                    </Snackbar>
                </Grid>}
        </Grid>
    );
}

export default ProfileCard;