import React from "react";

import OxygenAvatar from "../shared/OxygenAvatar.jsx";

import { Grid, TextField, Button, LinearProgress, Typography } from "@mui/material";

import { useState, useContext } from "react";

import UserContext from "./UserContext.jsx";


export default function GeneralSection({ showMessage }) {
    // State variable indicating whether the edit mode is active.
    const [isEditActive, setIsEditActive] = useState(false);

    const userContext = useContext(UserContext);
    const currentUserData = userContext.currentUserData;
    const updateCurrentUser = userContext.updateCurrentUser;

    // State variable for holding the edited user name.
    const [editedUserName, setEditedUserName] = useState(currentUserData.name);

    // State variable for holding the error message related to the edited name.
    const [editedNameError, setEditedNameError] = useState('');

    // State variable indicating whether the edit submission is in progress.
    const [isLoading, setIsLoading] = useState(false);


    /**
     * Handle input change event for text field.
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
     * Send an edit name request to the server.
     */
    const sendEditNameRequest = () => {
        setIsLoading(true);

        return fetch('api/users/profile', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(editedUserName),
        }).then((response) => {
            if (response.ok) {
                setIsLoading(false);
                setIsEditActive(false);

                showMessage(true, 'Profile has been updated!')
            }
            return response.json();
        }).then((data) => {
            if (data.errors) {
                if (data.errors[0].fieldName === 'name') {
                    setEditedNameError(data.errors[0].errorMessage);
                }
            } else if (data.errorMessage) {
                throw new Error(data.errorMessage);
            } else {
                updateCurrentUser(data);
            }
        }).catch(error => {
            setIsLoading(false);

            showMessage(false, error.name == "TypeError" ? "The connection could not be established." : error.message);
        });
    };

    /**
     * Initiates the save process if changes are valid when the save button is clicked.
     */
    const handleClickSaveButton = () => {
        if (editedUserName === currentUserData.name) {
            setIsEditActive(false);
        } else if(validateEditedInputs()) {
            sendEditNameRequest();
        }
    }

    return (
        <Grid item container direction='column' gap='20px' >
            <Grid item style={{ padding: '10px' }}>
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
                                disabled={isLoading}
                                style={{ background: 'lightgray', color: 'black' }}>
                                Cancel
                            </Button>
                        </Grid>

                        {/* Save button */}
                        <Grid item>
                            <Button id="save-button"
                                variant="contained"
                                disabled={isLoading}
                                onClick={handleClickSaveButton}>
                                Save
                            </Button>
                        </Grid>
                    </Grid>

                ) : (
                    // Edit button
                    <Grid item>
                        <Button
                            id="edit-button"
                            variant="contained"
                            onClick={handleClickEditButton}
                            disabled={currentUserData.status === 'deleted'}>
                            Edit
                        </Button>
                    </Grid>
                )}
            </Grid>

            {/* Display a progress bar during edit submission */}
            {
                isLoading &&
                <Grid item xs>
                    <LinearProgress />
                </Grid>
            }
        </Grid >
    );
};