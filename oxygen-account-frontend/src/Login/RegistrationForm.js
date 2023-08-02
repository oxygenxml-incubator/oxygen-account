import React from 'react';
import Button from '@mui/material/Button';

import { useState } from 'react';
import { TextField, Snackbar, CircularProgress } from '@mui/material';
import Alert from '@mui/material/Alert';
import Grid from '@mui/material/Grid';

function RegistrationForm() {
    
    // State variable for the user's name.
    const [name, setName] = useState('');

    // State variable for the user's email address.
    const [email, setEmail] = useState('');

    // State variable for the user's password.
    const [password, setPassword] = useState('');

    // State variable for confirming the user's password.
    const [confirmPassword, setConfirmPassword] = useState('');

    // State variable for storing error message related to the name field.
    const [nameError, setNameError] = useState('');

    // State variable for storing error message related to the email field.
    const [emailError, setEmailError] = useState('');

    // State variable for storing error message related to the password field.
    const [passwordError, setPasswordError] = useState('');

    // State variable for storing error message related to the confirm password field.
    const [confirmPasswordError, setConfirmPasswordError] = useState('');

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

        if (id === "name-input") {
            setName(value);

            if(nameError !== '') {
                setNameError('');
            }
        }
        else if (id === "email-input") {
            setEmail(value);

            if(emailError !== '') {
                setEmailError('');
            }
        }
        else if (id === "password-input") {
            setPassword(value);

            if(passwordError !== '') {
                setPasswordError('');
            }
        }
        else if (id === "confirmPassword-input") {
            setConfirmPassword(value);

            if(confirmPasswordError !== '') {
                setConfirmPasswordError('');
            }
        }
    }


    const clearInputFields = () => {
        setName('');
        setEmail('');
        setPassword('');
        setConfirmPassword('');
    }

    /**
     * Send a registration request to the server to create a new user account.
     * @param {Object} newUser - The user object with name, email, and password properties.
     * @returns {Promise} A promise that resolves with the response data from the server.
     * @throws {Error} If the response from the server contains an errorMessage.
     */
    const sendRegistrationRequest = (newUser) => {
        return fetch('api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newUser),
        })
            .then((response) => {
                if (response.ok) {
                    clearInputFields();

                    setisLoadingActive(false);

                    setIsSuccessSnackbar(true);
                    setSnackbarMessage('Account created successfully!');
                    setShowSnackbar(true);
                }
                return response.json();
            })
            .then((data) => {
                if (data.errorMessage) {
                    throw new Error(data.errorMessage);
                }
            })
            .catch(error => {
                setisLoadingActive(false);

                setIsSuccessSnackbar(false);

                // If the error name is 'TypeError', it means there was a connection error.
                // Otherwise, display the error message from the error object.
                setSnackbarMessage(error.name == "TypeError" ? "The connection could not be established." : error.message);

                setShowSnackbar(true);
            });
    };


    /**
    * Validates the input fields for user registration.
    * @returns {boolean} True if all input fields are valid, otherwise false.
    */
    const validateInputs = () => {
        let isNameValid = name.trim() !== '';
        let isEmailValid = email.trim() !== '' && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
        let isPasswordValid = /^.{8,}$/.test(password.trim());
        let isConfirmPasswordValid = confirmPassword.trim() !== '' && password === confirmPassword;

        // Set an error message for each field if it is invalid.
        setNameError(isNameValid ? '' : 'Invalid name.');
        setEmailError(isEmailValid ? '' : 'Invalid email.');
        setPasswordError(isPasswordValid ? '' : 'Password must be at least 8 characters.');
        setConfirmPasswordError(isConfirmPasswordValid ? '' : 'Passwords do not match.');

        return isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid;
    }

    /*
     * Handle click event of the registration button.
     */
    const handleClickButton = () => {
        // Validate the input fields before proceeding with registration.
        if (validateInputs()) {
            setisLoadingActive(true);

            // Create a new user object.
            const newUser = {
                name: name.trim(),
                email: email.trim(),
                password: password.trim(),
            };

            sendRegistrationRequest(newUser);
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
            <Grid container spacing={3} style={{ margin: '20px' }}>
                <Grid item xs={12}>
                    <h1>Create new account</h1>
                    <h3>Already a member? Log In</h3>
                </Grid>

                {/* Name input field */}
                <Grid item xs={12}>
                    <TextField
                        id="name-input"
                        label="Name"
                        variant="outlined"
                        value={name}
                        sx = {{width: '550px'}}
                        onChange={handleInputChange}
                        error={Boolean(nameError)}
                        helperText={nameError}
                    />
                </Grid>

                {/* Email input field */}
                <Grid item xs={12}>
                    <TextField
                        id="email-input"
                        label="Email"
                        variant="outlined"
                        value={email}
                        sx = {{width: '550px'}}
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
                        sx = {{width: '550px'}}
                        onChange={handleInputChange}
                        error={Boolean(passwordError)}
                        helperText={passwordError}
                    />
                </Grid>

                {/* Confirm Password input field */}
                <Grid item xs={12}>
                    <TextField
                        id="confirmPassword-input"
                        label="Confirm Password"
                        type="password"
                        value={confirmPassword}
                        sx = {{width: '550px'}}
                        onChange={handleInputChange}
                        error={Boolean(confirmPasswordError)}
                        helperText={confirmPasswordError}
                    />
                </Grid>

                {/* Loading indicator and "Create account" button */}
                <Grid item xs={12} display="flex">
                    <CircularProgress style={{ marginLeft: '110px', visibility: isLoadingActive ? 'visible' : 'hidden' }} />

                    <Button onClick={handleClickButton} variant="contained" style={{ marginLeft: '30px' }} disabled={isLoadingActive}>
                        Create account
                    </Button>
                </Grid>

                {/* Snackbar for showing success or error messages */}
                <Grid item xs={12}>
                    <Snackbar
                        open={showSnackbar}
                        autoHideDuration={5000}
                        onClose={handleSnackbarClose}
                        sx = {{width: '550px', margin: '25px'}}
                    >
                        <Alert
                            elevation={6}
                            variant="filled"
                            onClose={handleSnackbarClose}
                            severity={isSuccessSnackbar ? 'success' : 'error'}
                            sx = {{width: '100%'}}
                        >
                            {snackbarMessage}
                        </Alert>
                    </Snackbar>
                </Grid>
            </Grid>
        </form>

    );
};

export default RegistrationForm;