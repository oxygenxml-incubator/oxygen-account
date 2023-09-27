import React, { useState } from 'react';
import { Grid, Card, CardHeader, CardContent, Typography, Link } from '@mui/material';
import LoginForm from './LoginForm.jsx';
import RegistrationForm from './RegistrationForm.jsx';

/**
 * Component responsible for rendering the authentication card containing login and registration forms.
 * 
 * @returns {JSX.Element} The JSX representation of the AuthCard component.
 */
function AuthCard() {
    // State variable for managing whether to show login or registration form
    const [showLoginForm, setShowLoginForm] = useState(true);

    /**
    * Toggles between login and registration forms and remove hash.
    */
    const toggleForm = () => {
        if (window.location.hash) {
            history.replaceState({}, document.title, window.location.pathname);
        }
        setShowLoginForm(!showLoginForm);
    };

    return (
        // Main container for the authentication card
        <Grid container justifyContent="center" alignItems="center">
            <Grid item xs={12} md={9} lg={5} xl={5} style={{ marginTop: 50 }}>
                {/* Card for containing login/registration content */}
                <Card>
                    {/* Header for displaying title and the message from subheader */}
                    <CardHeader
                        title={showLoginForm ? 'Log In' : 'Create New Account'}

                        subheader={
                            <Grid container alignItems="center" spacing={1}>
                                {/* Subheader text */}
                                <Grid item>
                                    <Typography>
                                        {showLoginForm ? "Don't have an Oxygen Account?" : 'Already a member?'}
                                    </Typography>
                                </Grid>
                                {/* Link to toggle between login and registration forms */}
                                <Grid item>
                                    <Link
                                        component="button"
                                        underline='none'
                                        onClick={(e) => {
                                            toggleForm();
                                        }}
                                    >
                                        {showLoginForm ? ' Create an account' : ' Log In'}
                                    </Link>
                                </Grid>
                            </Grid>
                        }
                    />
                    {/* Render either the login or registration form based on the state */}
                    <CardContent>
                        {showLoginForm ? <LoginForm /> : <RegistrationForm />}
                    </CardContent>
                </Card>
            </Grid>
        </Grid>
    );
}

export default AuthCard;