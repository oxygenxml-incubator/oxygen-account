import React, { useState, useEffect } from 'react';
import {
    Grid, Card, CardHeader, CardContent,
    LinearProgress, Typography, Snackbar, Alert,
    TextField, Button
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

    // State variable indicating whether the change password view is active or not.
    const [isChangePasswordViewActive, setIsChangePasswordViewActive] = useState(false);

    // State variable for holding the current password.
    const [currentPassword, setCurrentPassword] = useState('');

    // State variable for holding the new password.
    const [newPassword, setNewPassword] = useState('');

    // State variable for confirming the new password.
    const [confirmNewPassword, setConfirmNewPassword] = useState('');

    // State variable for holding the error message related to the current password.
    const [currentPasswordError, setCurrentPasswordError] = useState('');

    // State variable for holding the error message related to the new password.
    const [newPasswordError, setNewPasswordError] = useState('');

    // State variable for holding the error message for confirming the new password.
    const [confirmNewPasswordError, setConfirmNewPasswordError] = useState('');

    // State variable indicating whether a change password submission is in progress.
    const [isChangePasswordSubmissionInProgress, setIsChangePasswordSubmissionInProgress] = useState(false);

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

        else if (id === "current-password") {
            setCurrentPassword(value);

            if (currentPasswordError !== '') {
                setCurrentPasswordError('');
            }
        }

        else if (id === "new-password") {
            setNewPassword(value);

            if (newPasswordError !== '') {
                setNewPasswordError('');
            }
        }

        else if (id === "confirm-new-password") {
            setConfirmNewPassword(value);

            if (confirmNewPasswordError !== '') {
                setConfirmNewPasswordError('');
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

    /**
     * Activates the change password view.
     */
    const handleChangePasswordClick = () => {
        setIsChangePasswordViewActive(true);
    }

    /**
     * Clears all password-related fields and errors.
     */
    const clearPasswordFileds = () => {
        setCurrentPassword('');
        setNewPassword('');
        setConfirmNewPassword('');

        setCurrentPasswordError('');
        setNewPasswordError('');
        setConfirmNewPasswordError('');
    }


    /**
     * Deactivates the change password view and clears fields.
     */
    const handleCancelChangePasswordClick = () => {
        setIsChangePasswordViewActive(false);

        clearPasswordFileds();
    }

    /**
     * Validates the new password.
     * @returns {boolean} - True if the new password is valid; otherwise, false.
     */
    const validateNewPassword = () => {
        let isNewPasswordValid = /^.{8,}$/.test(newPassword.trim());
        let isConfirmNewPasswordValid = newPassword === confirmNewPassword;
        let isNewPasswordUsed = currentPassword === newPassword;

        if (!isNewPasswordValid) {
            setNewPasswordError('Input field is too short. Please enter a longer value.');
        } else if (isNewPasswordUsed) {
            setNewPasswordError('The new password is the same as the old one.');
        } else {
            setNewPasswordError('');
        }
        setConfirmNewPasswordError(isConfirmNewPasswordValid ? '' : 'Passwords do not match.');

        return isNewPasswordValid && isConfirmNewPasswordValid && !isNewPasswordUsed;
    }

    /**
     * Sends a request to change the password.
     */
    const sendChangePasswordRequest = () => {
        setIsChangePasswordSubmissionInProgress(true);

        const updatePasswordInfo = {
            oldPassword: currentPassword.trim(),
            newPassword: newPassword.trim(),
        };

        return fetch('api/users/password', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatePasswordInfo),
        })
            .then((response) => {
                if (response.ok) {
                    setIsChangePasswordSubmissionInProgress(false);
                    setIsChangePasswordViewActive(false);
                    clearPasswordFileds();

                    setIsSuccessSnackbar(true);
                    setSnackbarMessage('The password has been changed successfully.');
                    setShowSnackbar(true);
                }
                return response.json();
            })
            .then((data) => {
                setIsChangePasswordSubmissionInProgress(false);

                if (data.errors) {
                    data.errors.forEach(error => {
                        switch (error.fieldName) {
                            case "oldPassword":
                                setCurrentPasswordError(error.errorMessage);
                                break;
                            case "newPassword":
                                setNewPasswordError(error.errorMessage);
                                break;
                        }
                    });
                } else if (data.errorMessage) {
                    throw new Error(data.errorMessage);
                }
            })
            .catch(error => {
                setIsChangePasswordSubmissionInProgress(false);

                setIsSuccessSnackbar(false);

                // If the error name is 'TypeError', it means there was a connection error.
                // Otherwise, display the error message from the error object.
                setSnackbarMessage(error.name == "TypeError" ? "The connection could not be established." : error.message);

                setShowSnackbar(true);
            });
    };

    /**
    * Handles the click event for saving the new password.
     * Validates the new password and initiates the change password request if valid.
     */
    const handleSavePasswordClick = () => {
        if (validateNewPassword()) {
            sendChangePasswordRequest();
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
                            <Grid container direction='column' gap='50px'>
                                {/* General section */}
                                <Grid item container direction = 'column' gap='20px'>
                                    <Grid item>
                                        <Typography variant="h6">
                                            General
                                        </Typography>
                                    </Grid>
                                    <Grid item container gap='10px'>
                                        {/* Display user avatar */}
                                        <Grid item style={{ maxWidth: 'fit-content' }}>
                                            <OxygenAvatar
                                                name={currentUserData.name}
                                                size={"100px"}
                                            />
                                        </Grid>

                                        {/* Display user info */}
                                        <Grid item container style={{ flexGrow: 1, flexBasis: 0 }} direction='column' gap='15px'>
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
                                            <Grid item container style={{ maxWidth: 'fit-content' }} gap='10px'>
                                                {/* Cancel button */}
                                                <Grid item>
                                                    <Button
                                                        variant="contained"
                                                        onClick={handleClickCancelEditButton}
                                                        disabled={isEditSubmissionInProgress}
                                                        style={{ background: 'lightgray', color: 'black' }}>
                                                        Cancel
                                                    </Button>
                                                </Grid>

                                                {/* Save button */}
                                                <Grid item>
                                                    <Button id="save-button"
                                                        variant="contained"
                                                        disabled={isEditSubmissionInProgress}
                                                        onClick={handleClickSaveButton}>
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

                                {/* Security section */}
                                <Grid item container direction='column' style={{ borderTop: "1px solid #333"}} gap='20px'>
                                    {/* Security Title */}
                                    <Grid item>
                                        <Typography variant="h6">
                                            Security
                                        </Typography>
                                    </Grid>

                                    {/* Change Password Input Fields */}
                                    {isChangePasswordViewActive &&
                                        <Grid item container direction="column" gap='15px'>
                                            {/* Current Password Input */}
                                            <Grid item>
                                                <TextField
                                                    id="current-password"
                                                    label="Current Password"
                                                    type="password"
                                                    value={currentPassword}
                                                    onChange={handleInputChange}
                                                    error={Boolean(currentPasswordError)}
                                                    helperText={currentPasswordError}
                                                    fullWidth
                                                />
                                            </Grid>

                                            {/* New Password Input */}
                                            <Grid item>
                                                <TextField
                                                    id="new-password"
                                                    label="New Password"
                                                    type="password"
                                                    value={newPassword}
                                                    onChange={handleInputChange}
                                                    error={Boolean(newPasswordError)}
                                                    helperText={newPasswordError}
                                                    fullWidth
                                                />
                                            </Grid>

                                            {/* Confirm New Password Input */}
                                            <Grid item>
                                                <TextField
                                                    id="confirm-new-password"
                                                    label="Confirm New Password"
                                                    type="password"
                                                    value={confirmNewPassword}
                                                    onChange={handleInputChange}
                                                    error={Boolean(confirmNewPasswordError)}
                                                    helperText={confirmNewPasswordError}
                                                    fullWidth
                                                />
                                            </Grid>
                                        </Grid>}

                                    {/* Conditionally render buttons based on Change Password Mode */}
                                    <Grid item container justifyContent="flex-end">
                                        <Grid item container justifyContent="flex-end">
                                            {isChangePasswordViewActive ? (
                                                <Grid item container style={{ maxWidth: 'fit-content' }} gap='10px'>
                                                    { /*  Save and Cancel Buttons */}
                                                    <Grid item>
                                                        <Button
                                                            variant="contained"
                                                            onClick={handleCancelChangePasswordClick}
                                                            style={{ background: 'lightgray', color: 'black' }}>
                                                            Cancel
                                                        </Button>
                                                    </Grid>

                                                    <Grid item>
                                                        <Button
                                                            id = "save-password-button"
                                                            disabled={currentPassword === '' || newPassword === '' || confirmNewPassword === ''}
                                                            variant="contained"
                                                            onClick={handleSavePasswordClick}>
                                                            Save Password
                                                        </Button>
                                                    </Grid>
                                                </Grid>

                                            ) : (
                                                // Change Password Button
                                                <Grid item>
                                                    <Button
                                                        id = "change-password-button"
                                                        variant="contained"
                                                        onClick={handleChangePasswordClick}>
                                                        Change Password
                                                    </Button>
                                                </Grid>
                                            )}
                                        </Grid>
                                    </Grid>

                                    {/* Display a Progress Bar during Change Password Submission */}
                                    {isChangePasswordSubmissionInProgress &&
                                        <Grid item xs>
                                            <LinearProgress />
                                        </Grid>}
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