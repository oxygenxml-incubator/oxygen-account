import React, { useState, useEffect } from 'react';
import {
    Grid, Card, CardHeader, CardContent,
    LinearProgress, Typography, Snackbar, Alert,
    TextField,
    Button
} from '@mui/material';

import OxygenAvatar from "../shared/OxygenAvatar.jsx";

/**
 * Component responsible for rendering the profile card containing current user info.
 * 
 * @returns {JSX.Element} The JSX representation of the ProfileCard component.
 */
function ProfileCard() {
    // State variable for holding user data
    const [currentUserData, setCurrentUserData] = useState(null);

    // State variable indicating when the request is in progress.
    const [isDataLoadingActive, setIsDataLoadingActive] = useState(true);

    // State variable for showing or hiding the Snackbar component.
    const [showSnackbar, setShowSnackbar] = useState(false);

    // State variable for storing the message to display in the Snackbar.
    const [snackbarMessage, setSnackbarMessage] = useState('');

    // State variable indicating whether the Snackbar should display success or error severity.
    const [isSuccessSnackbar, setIsSuccessSnackbar] = useState(false);

    // State variable indicating whether the edit mode is active.
    const [isEditActive, setIsEditActive] = useState(false);

    // State variable for holding the edited user name.
    const [editedUserName, setEditedUserName] = useState('');

    // State variable for holding the error message related to the edited name.
    const [editedNameError, setEditedNameError] = useState('');

    // State variable indicating whether the edit submission is in progress.
    const [isEditSubmissionInProgress, setIsEditSubmissionInProgress] = useState(false);

    /**
     * Fetch user data on component mount
     */
    useEffect(() => {
        setIsDataLoadingActive(true);

        fetch('api/users/me', {
            method: 'GET'
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                setIsDataLoadingActive(false);

                if (data.errorMessage) {
                    throw new Error(data.errorMessage);
                }

                setCurrentUserData(data);
                setEditedUserName(data.name);
            })
            .catch(error => {
                setIsDataLoadingActive(false);

                setSnackbarMessage(error.message);

                setShowSnackbar(true);
            });

    }, []);

    /*
     * Handle the Snackbar close event.
     */
    const handleSnackbarClose = () => {
        setShowSnackbar(false);
    };

    /**
     * Handle input change event.
     *
     * @param {Event} event - The input change event.
     */
    const handleInputChange = (event) => {
        const { id, value } = event.target;

        if (id === "name-info") {
            setEditedUserName(value);

            if (editedNameError !== '') {
                setEditedNameError('');
            }
        }
    }

    /**
     * Validate edited inputs.
     *
     * @returns {boolean} true if the edited inputs are valid.
     */
    const validateEditedInputs = () => {
        let isEditedNameValid = editedUserName.trim() !== '';
        setEditedNameError(isEditedNameValid ? '' : 'Please provide a non-empty value.');

        return isEditedNameValid;
    }

    /**
     *Activates the edit mode when the edit button is clicked.
     */
    const handleClickEditButton = () => {
        setIsEditActive(true);
    };

    /**
     * Cancels the edit mode and reverts any unsaved changes when the cancel edit button is clicked.
     */
    const handleClickCancelEditButton = () => {
        // Revert name if it was changed
        if (editedUserName !== currentUserData.name) {
            setEditedUserName(currentUserData.name);
            setEditedNameError('');
        }

        setIsEditActive(false);
    };


    /**
     * Send an edit name request to the server.
     */
    const sendEditNameRequest = () => {
        setIsEditSubmissionInProgress(true);

        return fetch('api/users/profile', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(editedUserName),
        })
            .then((response) => {
                if (response.ok) {
                    setIsEditSubmissionInProgress(false);
                    setIsEditActive(false);
                    currentUserData.name = editedUserName;

                    setIsSuccessSnackbar(true);
                    setSnackbarMessage('Profile has been updated!');
                    setShowSnackbar(true);
                }
                return response.json();
            })
            .then((data) => {
                setIsEditSubmissionInProgress(false);

                if (data.errors) {
                    if (data.errors[0].fieldName === 'name') {
                        setEditedNameError(data.errors[0].errorMessage);
                    }
                } else if (data.errorMessage) {
                    throw new Error(data.errorMessage);
                }
            })
            .catch(error => {
                setIsEditSubmissionInProgress(false);

                setIsSuccessSnackbar(false);

                // If the error name is 'TypeError', it means there was a connection error.
                // Otherwise, display the error message from the error object.
                setSnackbarMessage(error.name == "TypeError" ? "The connection could not be established." : error.message);

                setShowSnackbar(true);
            });
    };

    /**
     * Initiates the save process if changes are valid when the save button is clicked.
     */
    const handleClickSaveButton = () => {
        if (editedUserName === currentUserData.name) {
            setIsEditActive(false);
        } else if (validateEditedInputs()) {
            sendEditNameRequest();
        }
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
                            <Grid container spacing={2}>
                                <Grid item container direction="row" spacing={2}>
                                    {/* Display user avatar */}
                                    <Grid item style={{ maxWidth: 'fit-content' }}>
                                        <OxygenAvatar
                                            name={currentUserData.name}
                                            size={"100px"}
                                        />
                                    </Grid>

                                    {/* Display user info */}
                                    <Grid item container style={{ flexGrow: 1, flexBasis: 0 }} direction='column' spacing={2}>
                                        {/* Input field for showing and editing the name */}
                                        <Grid item>
                                            <TextField
                                                id="name-info"
                                                label="Name"
                                                variant="outlined"
                                                value={editedUserName}
                                                InputProps={{ readOnly: !isEditActive }}
                                                onChange={handleInputChange}
                                                error={Boolean(editedNameError)}
                                                helperText={editedNameError}
                                                fullWidth
                                            />
                                        </Grid>

                                        {/* Display user's email */}
                                        <Grid item>
                                            <TextField
                                                id="email-info"
                                                label="Email"
                                                variant="outlined"
                                                value={currentUserData.email}
                                                InputProps={{ readOnly: true }}
                                                fullWidth
                                            />
                                        </Grid>
                                    </Grid>
                                </Grid>

                                {/* Conditionally render buttons based on Edit Mode */}
                                <Grid item container justifyContent="flex-end">
                                    {isEditActive ? (
                                        <Grid item container style={{ maxWidth: 'fit-content' }} spacing={1}>
                                            {/* Cancel button */}
                                            <Grid item>
                                                <Button
                                                    variant="contained"
                                                    onClick={handleClickCancelEditButton}
                                                    style={{ background: 'lightgray', color: 'black' }}>
                                                    Cancel
                                                </Button>
                                            </Grid>

                                            {/* Save button */}
                                            <Grid item>
                                                <Button id="save-button" variant="contained" onClick={handleClickSaveButton}>
                                                    Save
                                                </Button>
                                            </Grid>
                                        </Grid>

                                    ) : (
                                        // Edit button
                                        <Grid item>
                                            <Button id="edit-button" variant="contained" onClick={handleClickEditButton}>
                                                Edit
                                            </Button>
                                        </Grid>
                                    )}
                                </Grid>

                                {/* Display a progress bar during edit submission */}
                                {isEditSubmissionInProgress &&
                                <Grid item xs>
                                    <LinearProgress />
                                </Grid>}
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