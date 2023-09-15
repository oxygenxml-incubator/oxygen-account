import React from "react";

import { useState, useContext } from "react";

import { Grid, TextField, Button, Typography, LinearProgress } from "@mui/material";

import UserContext from "./UserContext.jsx";

export default function SecuritySection({ showMessage }) {
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
    const [isLoading, setIsLoading] = useState(false);

    const userContext = useContext(UserContext);
    const currentUserData = userContext.currentUserData;

    /**
     * Handle input change event for text field.
     */
    const handleInputChange = (event) => {
        const { id, value } = event.target;

        if (id === "current-password") {
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
     * Deactivates the change password view and clears fields.
     */
    const handleCancelChangePasswordClick = () => {
        setIsChangePasswordViewActive(false);

        clearPasswordFileds();
    }

    /**
     * Sends a request to change the password.
     */
    const sendChangePasswordRequest = () => {
        setIsLoading(true);

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
        }).then((response) => {
            if (response.ok) {
                setIsLoading(false);
                setIsChangePasswordViewActive(false);
                clearPasswordFileds();

                showMessage(true, 'The password has been changed successfully.');
            }
            return response.json();
        }).then((data) => {
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
        }).catch(error => {
            setIsLoading(false);

            showMessage(false, error.name == "TypeError" ? "The connection could not be established." : error.message);
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
        <Grid item container direction='column' style={{ borderTop: "1px solid #333" }} gap='15px'>
            {/* Security Title */}
            <Grid item style={{ padding: '10px' }}>
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
                                    id="save-password-button"
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
                                id="change-password-button"
                                variant="contained"
                                onClick={handleChangePasswordClick}
                                disabled={(currentUserData.status === 'deleted')}>
                                Change Password
                            </Button>
                        </Grid>
                    )}
                </Grid>
            </Grid>

            {/* Display a Progress Bar during Change Password Submission */}
            {isLoading &&
                <Grid item xs>
                    <LinearProgress />
                </Grid>}
        </Grid>
    )
};