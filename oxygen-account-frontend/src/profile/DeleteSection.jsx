import React from "react";

import { useState, useContext } from "react";

import {
    Grid, Typography, Dialog, DialogTitle, DialogContentText,
    DialogContent, DialogActions, TextField, Button, LinearProgress
} from "@mui/material";

import UserContext from "./UserContext.jsx";

// The number of milliseconds in a day.
const MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;

// The maximum number of days allowed for account recovery.
const MAX_DAYS_FOR_RECOVERY = 7;

export default function DeleteSection({ showMessage }) {
    // State variable for showing the delete account dialog.
    const [isDeleteAccountDialogActive, setIsDeleteAccountDialogActive] = useState(false);

    // State variable for holding the password for confirming deletion.
    const [deletePassword, setDeletePassword] = useState('');

    // State variable holding the error message related to the password field for confirming deletion.
    const [deletePasswordError, setDeletePasswordError] = useState('');

    // State variable indicating whether a delete account submission is currently in progress.
    const [isDeleteAccountSubmissionInProgress, setIsDeleteAccountSubmissionInProgress] = useState(false);

    // State variable indicating whether a recover account submission is currently in progress.
    const [isLoading, setIsLoading] = useState(false);

    // Using the useContext hook to obtain the current context value for UserContext, and storing it in the userContext variable.
    const userContext = useContext(UserContext);

    // Accessing the currentUserData property from the userContext object to get the data of the currently logged-in user.
    const currentUserData = userContext.currentUserData;

    // Accessing the updateCurrentUser function from the userContext object to have a function that updates the current user's data.
    const updateCurrentUser = userContext.updateCurrentUser;


    /**
     * Handle input change event for text field.
     */
    const handleInputChange = (event) => {
        const { id, value } = event.target;

        if (id === "delete-password-field") {
            setDeletePassword(value);

            if (deletePasswordError !== '') {
                setDeletePasswordError('');
            }
        }
    }

    /**
     * Opens the pop up for deleting the user account.
     */
    const handleDeleteAccountClick = () => {
        setIsDeleteAccountDialogActive(true);
    }

    /**
     * Closes the dialog for deleting the user account and clear its fields.
     */
    const handleCloseDeleteAccountDialog = () => {
        setIsDeleteAccountDialogActive(false);

        setDeletePassword('');
        setDeletePasswordError('');
    }


    /**
     * Sends a request to delete the user account.
     */
    const sendDeleteAccountRequest = () => {
        setIsDeleteAccountSubmissionInProgress(true);

        const deletePasswordInfo = {
            password: deletePassword.trim()
        };

        return fetch('api/users/delete', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(deletePasswordInfo),
        }).then((response) => {
            setIsDeleteAccountSubmissionInProgress(false);

            if (response.ok) {
                handleCloseDeleteAccountDialog();
            }

            return response.json();
        }).then((data) => {
            if (data.errorMessage) {
                if (data.messageId === 'INCORRECT_PASSWORD') {
                    setDeletePasswordError(data.errorMessage);
                } else {
                    throw new Error(data.errorMessage);
                }
            } else {
                // If there are no errors, update the user's information.
                updateCurrentUser(data);
            }
        }).catch(error => {
            setIsDeleteAccountSubmissionInProgress(false);

            showMessage(false, error.name == "TypeError" ? "The connection could not be established." : error.message);
        });
    };

    /**
     * Confirms the deletion of the user account.
     */
    const handleConfirmDeleteAccount = () => {
        sendDeleteAccountRequest();
    }

    /**
     * Sends a request to recover the user account.
     */
    const sendRecoverAccountRequest = () => {
        setIsLoading(true);

        return fetch('api/users/recover', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            setIsLoading(false);
            return response.json();
        }).then((data) => {
            if (data.errorMessage) {
                throw new Error(data.errorMessage);
            } else {
                // If there are no errors, update the user's information.
                updateCurrentUser(data);
            }
        }).catch(error => {
            setIsLoading(false);
            
            showMessage(false, error.name == "TypeError" ? "The connection could not be established." : error.message);
        });
    };

    /**
     * Handles the click event for recovering the user account.
     */
    const handleRecoverAccountClick = () => {
        sendRecoverAccountRequest();
    }

    /**
     * Calculates the number of days left for recovery.
     */
    const calculateDaysLeftForRecovery = () => {
        const daysRemaining = MAX_DAYS_FOR_RECOVERY - Math.floor((new Date() - new Date(currentUserData.deletionDate)) / MILLISECONDS_PER_DAY)
        return Math.max(0, daysRemaining);
    };

    return (
        (currentUserData.status === 'deleted') ? (
            <Grid item container direction='column' style={{ borderTop: "1px solid #333" }} gap='15px'>
                <Grid item container direction='column' style={{ padding: '10px' }}>
                    <Grid item>
                        <Typography variant="h6">
                            Recover account
                        </Typography>
                    </Grid>

                    {currentUserData.deletionDate &&
                        <Grid item>
                            <Typography variant="h6" style={{ fontSize: "18px" }}>
                                {calculateDaysLeftForRecovery() > 1
                                    ? `Your account is marked as deleted. It is scheduled to be permanently deleted in ${calculateDaysLeftForRecovery()} days.`
                                    : `Your account is marked as deleted. It is scheduled to be permanently deleted today.`
                                }
                            </Typography>
                        </Grid>}
                </Grid>

                <Grid item container justifyContent="flex-end">
                    <Grid item>
                        <Button
                            id="recover-account-button"
                            variant="contained"
                            onClick={handleRecoverAccountClick}>
                            Recover
                        </Button>
                    </Grid>
                </Grid>

                {isLoading &&
                    <Grid item xs>
                        <LinearProgress />
                    </Grid>}
            </Grid>
        ) : (
            <Grid item container direction='column' style={{ borderTop: "1px solid #333" }} gap='15px'>
                <Grid item style={{ padding: '10px' }}>
                    <Typography variant="h6">
                        Delete account
                    </Typography>
                </Grid>

                <Grid item container justifyContent="flex-end">
                    <Grid item>
                        <Button
                            id="delete-account-button"
                            variant="contained"
                            color='error'
                            onClick={handleDeleteAccountClick}>
                            Delete
                        </Button>
                    </Grid>
                </Grid>

                {isDeleteAccountDialogActive &&
                    <Grid item>
                        <Dialog
                            open={isDeleteAccountDialogActive}
                            onClose={handleCloseDeleteAccountDialog}
                        >
                            <DialogTitle>
                                {"Delete account"}
                            </DialogTitle>
                            <DialogContent>
                                <Grid container direction='column' gap='15px'>
                                    <Grid item>
                                        <DialogContentText style={{ marginRight: '70px' }}>
                                            Insert password to delete your account
                                        </DialogContentText>
                                    </Grid>

                                    <Grid item>
                                        <TextField
                                            id="delete-password-field"
                                            label="Password"
                                            type="password"
                                            value={deletePassword}
                                            onChange={handleInputChange}
                                            error={Boolean(deletePasswordError)}
                                            helperText={deletePasswordError}
                                            fullWidth
                                        />
                                    </Grid>

                                    {isDeleteAccountSubmissionInProgress &&
                                        <Grid item xs>
                                            <LinearProgress />
                                        </Grid>}
                                </Grid>

                            </DialogContent>
                            <DialogActions>
                                <Button
                                    color='inherit'
                                    onClick={handleCloseDeleteAccountDialog}
                                    disabled={isDeleteAccountSubmissionInProgress}>
                                    Cancel
                                </Button>
                                <Button
                                    id='confirm-delete-account'
                                    disabled={isDeleteAccountSubmissionInProgress || deletePassword === ''}
                                    onClick={handleConfirmDeleteAccount}
                                    color='error'
                                    autoFocus>
                                    Delete account
                                </Button>
                            </DialogActions>
                        </Dialog>
                    </Grid>}
            </Grid>
        )
    );
};