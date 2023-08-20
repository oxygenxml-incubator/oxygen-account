import React, { useState } from 'react';
import { Grid, Card, CardHeader, CardContent, Typography, Link } from '@mui/material';
import LoginForm from './LoginForm.jsx';
import RegistrationForm from './RegistrationForm.jsx';

function CardForm() {
    const [showLoginForm, setShowLoginForm] = useState(true);

    const toggleForm = () => {
        setShowLoginForm(!showLoginForm);
    };

    return (
        <Grid container justifyContent="center" alignItems="center">
            <Grid item xs={5} md={5} lg={5} xl={5} style={{ marginTop: 50 }}>
                <Card>
                    <CardHeader
                        title={showLoginForm ? 'Log In' : 'Create New Account'}

                        subheader={
                            <Grid container alignItems="center" spacing={1}>
                                <Grid item>
                                    <Typography>
                                        {showLoginForm ? "Don't have an Oxygen Account?" : 'Already a member?'}
                                    </Typography>
                                </Grid>
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
                    <CardContent>
                        {showLoginForm ? <LoginForm /> : <RegistrationForm />}
                    </CardContent>
                </Card>
            </Grid>
        </Grid>
    );
}

export default CardForm;