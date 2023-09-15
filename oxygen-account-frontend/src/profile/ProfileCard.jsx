import React, { useState, useContext } from 'react';
import {
    Grid, Card, CardHeader, CardContent,
    LinearProgress, Snackbar, Alert,
} from '@mui/material';

import GeneralSection from './GeneralSection.jsx';
import SecuritySection from './SecuritySection.jsx';
import DeleteSection from './DeleteSection.jsx';
import UserContext from "./UserContext.jsx";

/**
 * Component responsible for rendering the profile card containing current user info.
 * 
 * @returns {JSX.Element} The JSX representation of the ProfileCard component.
 */
function ProfileCard() {  
    // State variable for showing or hiding the Snackbar component.
    const [showSnackbar, setShowSnackbar] = useState(false);

    // State variable for storing the message to display in the Snackbar.
    const [snackbarMessage, setSnackbarMessage] = useState('');

    // State variable indicating whether the Snackbar should display success or error severity.
    const [isSuccessSnackbar, setIsSuccessSnackbar] = useState(false);

    //const { currentUserData, updateCurrentUser, isDataLoadingActive } = useContext(UserContext);

    const userContext = useContext(UserContext);
    const currentUserData = userContext.currentUserData;
    const updateCurrentUser = userContext.updateCurrentUser;
    const isDataLoadingActive = userContext.isDataLoadingActive;

    /*
     * Handle the Snackbar close event.
     */
    const handleSnackbarClose = () => {
        setShowSnackbar(false);
    };

    /**
     * Sets the snackbar type and message.
     */
    const showMessage = ( type , content ) => {
        setIsSuccessSnackbar(type);
 
        setSnackbarMessage(content);

        setShowSnackbar(true);
    }


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
                        {isDataLoadingActive ? (
                            <LinearProgress />
                        ) : (
                            <Grid container direction='column' gap='30px'>
                                <GeneralSection 
                                    currentUserData={currentUserData}
                                    updateCurrentUser={updateCurrentUser}
                                    showMessage={showMessage }
                                />

                                <SecuritySection
                                    currentUserData={currentUserData}
                                    showMessage={showMessage}
                                />
                                
                                <DeleteSection
                                    currentUserData={currentUserData}
                                    updateCurrentUser={updateCurrentUser}
                                    showMessage={showMessage}
                                />
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
                            severity={isSuccessSnackbar ? 'success' : 'error'}
                        >
                            {snackbarMessage}
                        </Alert>
                    </Snackbar>
                </Grid>}
        </Grid>
    );
}

export default ProfileCard;