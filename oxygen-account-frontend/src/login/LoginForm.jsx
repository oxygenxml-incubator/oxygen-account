import React from 'react';
import { useState } from 'react';
import { TextField, Snackbar, LinearProgress, Button, Alert, Grid } from '@mui/material';

/**
 * Component for registering accounts in a database.
 * This component returns a form for users to provide their name, email, and password.
 */
function LoginForm({ toggleForm }) {


    // State variable for the user's email address.
    const [email, setEmail] = useState('');

    // State variable for the user's password.
    const [password, setPassword] = useState('');

    // State variable for storing error message related to the email field.
    const [emailError, setEmailError] = useState('');

    // State variable for storing error message related to the password field.
    const [passwordError, setPasswordError] = useState('');

    // State variable indicating when the form submission is in progress.
    const [isLoadingActive, setisLoadingActive] = useState(false);

    // State variable for showing or hiding the Snackbar component.
    const [showSnackbar, setShowSnackbar] = useState(false);

    // State variable for storing the message to display in the Snackbar.
    const [snackbarMessage, setSnackbarMessage] = useState('');


    /**
     * Handle input change event for text fields.
     * @param {Object} event - The input change event object.
     * @property {Object} event.target - The target element that triggered the event.
     * @property {string} event.target.id - The ID of the input field that triggered the change event.
     * @property {string} event.target.value - The new value of the input field.
     */
    const handleInputChange = (event) => {
        const { id, value } = event.target;

        if (id === "email") {
            setEmail(value);

            if (emailError !== '') {
                setEmailError('');
            }
        }
        else if (id === "password") {
            setPassword(value);

            if (passwordError !== '') {
                setPasswordError('');
            }
        }
    }

    /**
    * Validates the input fields for user authentication.
    * @returns {boolean} True if all input fields are valid, otherwise false.
    */
    const validateInputs = () => {
        let isEmailValid = email.trim() !== '';
        let isPasswordValid = password.trim() !== '';

        // Set an error message for each field if it is invalid.

        setEmailError(isEmailValid ? '' : 'Empty email.');
        setPasswordError(isPasswordValid ? '' : 'Empty password.');

        return isEmailValid && isPasswordValid;
    }

    /*
     * Handle the Snackbar close event.
     */
    const handleSnackbarClose = () => {
        setShowSnackbar(false);
    };

    // const handleSubmitForm = (event) => {
    //     if (!validateInputs()) {
    //         event.preventDefault();
    //     }
    //     else {
    //         if (window.location.hash === '#invalid-user') {
    //             event.preventDefault();
    //             setShowSnackbar(true);
    //             setSnackbarMessage('Invalid user');
    //         }
    //     }

    //     window.location.hash = '';
    // };

    const handleSubmitForm = (event) => {
        if (!validateInputs()) {
            event.preventDefault();
        }
    
     
    
        if (window.location.hash === '#invalid-user') {
            setShowSnackbar(true);
            setSnackbarMessage('Invalid user');
            window.location.hash = '';
            return;
        }
    };


    return (
        <form action="/login" method='POST' onSubmit={handleSubmitForm}>
            {/* Grid container for layout */}
            <Grid container spacing={3} justifyContent="center" alignItems="center">
                {/* Email input field */}
                <Grid item xs={12} md={12} lg={12} xl={12}>
                    <TextField
                        id="email"
                        name="email"
                        label="Email"
                        variant="outlined"
                        value={email}
                        onChange={handleInputChange}
                        error={Boolean(emailError)}
                        helperText={emailError}
                        fullWidth
                    />
                </Grid>

                {/* Password input field */}
                <Grid item xs={12} md={12} lg={12} xl={12}>
                    <TextField
                        id="password"
                        name="password"
                        label="Password"
                        type="password"
                        value={password}
                        onChange={handleInputChange}
                        error={Boolean(passwordError)}
                        helperText={passwordError}
                        fullWidth
                    />
                </Grid>

                {/* Loading indicator and "Log In" button */}
                <Grid item xs={3} md={3} lg={3} xl={3}>
                    <Button type="submit" variant="contained" disabled={isLoadingActive}>
                        Log In
                    </Button>
                </Grid>

                {isLoadingActive &&
                <Grid item  xs={12} md={12} lg={12} xl={12}>
                    <LinearProgress />
                </Grid>}

                {/* Snackbar for showing success or error messages */}
                {showSnackbar && 
                <Grid item xs={12} md={12} lg={12} xl={12}>
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
                            severity={'error'}
                            sx={{ width: '100%' }}
                        >
                            {snackbarMessage}
                        </Alert>
                    </Snackbar>
                </Grid> }
            </Grid>
        </form>

    );
};

export default LoginForm;