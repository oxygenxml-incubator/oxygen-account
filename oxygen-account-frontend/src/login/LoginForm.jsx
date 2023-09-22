import React from 'react';
import { useState, useEffect } from 'react';
import { TextField, Snackbar, Button, Alert, Grid } from '@mui/material';

/**
 * Component for verify accounts in a database.
 * This component returns a form for users to provide their email and password.
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

    // State variable for showing or hiding the Snackbar component.
    const [showSnackbar, setShowSnackbar] = useState(false);

    // State variable for storing the message to display in the Snackbar.
    const [snackbarMessage, setSnackbarMessage] = useState('');

    const [isSuccessSnackbar, setIsSuccesSnackbar] = useState(true);

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

    /**
     * Handles the form submission. Prevents form submission if inputs are not validated.
     * 
     * @param {Event} event - The form submission event.
     */
    const handleSubmitForm = (event) => {
        if (!validateInputs()) {
            event.preventDefault();
        }
    };

    /**
     * Checks the URL hash, and displays a Snackbar with a releated message.
     */
    function handleInvalidUserHash() {
        if (window.location.hash === '#invalid-user') {
            setIsSuccesSnackbar(false);
            setSnackbarMessage('Invalid email or password');
            setShowSnackbar(true);
        } else if (window.location.hash === '#success-confirmation') {
            setIsSuccesSnackbar(true);
            setSnackbarMessage('The account has been successfully verified');
            setShowSnackbar(true);
        } else if (window.location.hash === '#invalid-token') {
            setIsSuccesSnackbar(false);
            setSnackbarMessage('The confirmation link is invalid.');
            setShowSnackbar(true);
        } else if (window.location.hash === '#token-expired') {
            setIsSuccesSnackbar(false);
            setSnackbarMessage('The confirmation link has expired');
            setShowSnackbar(true);
        } else if (window.location.hash === '#user-already-confirmed') {
            setIsSuccesSnackbar(false);
            setSnackbarMessage('The user has already been confirmed.');
            setShowSnackbar(true);
        }
    }

    /**
     * Effect that calls the hash verification method on every page load.
     */
    useEffect(() => {
        handleInvalidUserHash();
     }, []);

    return (
        <form action="/login" method='POST' onSubmit={handleSubmitForm}>
            {/* Grid container for layout */}
            <Grid container spacing={3} direction="column" >
                {/* Email input field */}
                <Grid item>
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
                <Grid item>
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

                {/* "Log In" button */}
                <Grid item container justifyContent="center">
                    <Button type="submit" variant="contained">
                        Log In
                    </Button>
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