import React from 'react';
import { useState } from 'react';
import { TextField, Snackbar, CircularProgress, Button, Alert, Grid } from '@mui/material';

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

    // State variable indicating whether the Snackbar should display success or error severity.
    const [isSuccessSnackbar, setIsSuccessSnackbar] = useState(false);


    /**
     * Handle input change event for text fields.
     * @param {Object} event - The input change event object.
     * @property {Object} event.target - The target element that triggered the event.
     * @property {string} event.target.id - The ID of the input field that triggered the change event.
     * @property {string} event.target.value - The new value of the input field.
     */
    const handleInputChange = (event) => {
        const { id, value } = event.target;

        if (id === "email-input") {
            setEmail(value);

            if (emailError !== '') {
                setEmailError('');
            }
        }
        else if (id === "password-input") {
            setPassword(value);

            if (passwordError !== '') {
                setPasswordError('');
            }
        }
    }

    /**
    * This function clears the input fields.
    */
    const clearInputFields = () => {
        setEmail('');
        setPassword('');
    }

    // /**
    //  * Send a registration request to the server to create a new user account.
    //  * @param {Object} newUser - The user object with name, email, and password properties.
    //  * @returns {Promise} A promise that resolves with the response data from the server.
    //  * @throws {Error} If the response from the server contains an errorMessage.
    //  */
    // const sendRegistrationRequest = (newUser) => {
    //     return fetch('api/users/register', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json',
    //         },
    //         body: JSON.stringify(newUser),
    //     })
    //         .then((response) => {
    //             if (response.ok) {
    //                 clearInputFields();

    //                 setisLoadingActive(false);

    //                 setIsSuccessSnackbar(true);
    //                 setSnackbarMessage('Account created successfully!');
    //                 setShowSnackbar(true);
    //             }
    //             return response.json();
    //         })
    //         .then((data) => {
    //             if (data.errorMessage) {
    //                 throw new Error(data.errorMessage);
    //             }
    //         })
    //         .catch(error => {
    //             setisLoadingActive(false);

    //             setIsSuccessSnackbar(false);

    //             // If the error name is 'TypeError', it means there was a connection error.
    //             // Otherwise, display the error message from the error object.
    //             setSnackbarMessage(error.name == "TypeError" ? "The connection could not be established." : error.message);

    //             setShowSnackbar(true);
    //         });
    // };


    /**
    * Validates the input fields for user authentication.
    * @returns {boolean} True if all input fields are valid, otherwise false.
    */
    const validateInputs = () => {
        let isEmailValid = email.trim() !== '' && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
        let isPasswordValid = password.trim() !== '';

        // Set an error message for each field if it is invalid.

        setEmailError(isEmailValid ? '' : 'Invalid email.');
        setPasswordError(isPasswordValid ? '' : 'Empty password.');

        return isEmailValid && isPasswordValid;
    }

    /*
     * Handle click event of the log in button.
     */
    const handleClickButton = () => {
        // Validate the input fields before proceeding with registration.
        if (validateInputs()) {
            // setisLoadingActive(true);

            // // Create a new user object.
            // const newUser = {
            //     name: name.trim(),
            //     email: email.trim(),
            //     password: password.trim(),
            // };

            // sendRegistrationRequest(newUser);
        }

    }

    /*
     * Handle the Snackbar close event.
     */
    const handleSnackbarClose = () => {
        setShowSnackbar(false);
    };

    return (
        <form>
            {/* Grid container for layout */}
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <h1 style = {{marginLeft: "220px"}}>Log in</h1>
                    <div style={{ display: "flex", alignItems: "center" }}>
                        <h3 style = {{marginLeft: "70px"}}>Don't have an Oxygen Account?&nbsp;</h3>
                        <a href="#" onClick={(e) => { e.preventDefault(); toggleForm(); }} style={{ textDecoration: "none", fontSize: '20px', color: 'blue' }}>
                            Create an account
                        </a>
                        <h3>.</h3>
                    </div>
                </Grid>

                {/* Email input field */}
                <Grid item xs={12}>
                    <TextField
                        id="email-input"
                        label="Email"
                        variant="outlined"
                        value={email}
                        sx={{ width: '550px' }}
                        onChange={handleInputChange}
                        error={Boolean(emailError)}
                        helperText={emailError}
                    />
                </Grid>

                {/* Password input field */}
                <Grid item xs={12}>
                    <TextField
                        id="password-input"
                        label="Password"
                        type="password"
                        value={password}
                        sx={{ width: '550px' }}
                        onChange={handleInputChange}
                        error={Boolean(passwordError)}
                        helperText={passwordError}
                    />
                </Grid>

                {/* Loading indicator and "Create account" button */}
                <Grid item xs={12} display="flex">
                    <CircularProgress style={{ marginLeft: '110px', visibility: isLoadingActive ? 'visible' : 'hidden' }} />

                    <Button onClick={handleClickButton} variant="contained" style={{ marginLeft: '75px' }} disabled={isLoadingActive}>
                        Log In
                    </Button>
                </Grid>

                {/* Snackbar for showing success or error messages */}
                <Grid item xs={12}>
                    <Snackbar
                        open={showSnackbar}
                        autoHideDuration={5000}
                        onClose={handleSnackbarClose}
                        sx={{ width: '550px', margin: '25px' }}
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
                </Grid>
            </Grid>
        </form>

    );
};

export default LoginForm;