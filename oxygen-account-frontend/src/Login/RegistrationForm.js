import React from 'react';
import Button from '@mui/material/Button';

import { useState } from 'react';
import { TextField } from '@mui/material';
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Grid';


const Item = styled("div")(({ theme }) => ({
    padding: theme.spacing(1.5),

    textAlign: "left"
}));

function RegistrationForm() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [nameError, setNameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [confirmPasswordError, setConfirmPasswordError] = useState('');


    const handleInputChange = (event) => {
        const { id, value } = event.target;

        if (id === "name-input") {
            setName(value);
        }
        else if (id === "email-input") {
            setEmail(value);
        }
        else if (id === "password-input") {
            setPassword(value);
        }
        else if (id === "confirmPassword-input") {
            setConfirmPassword(value);
        }
    }

    const handleClickButton = () => {
        setNameError(name.trim() === '' ? 'Name required' : '');

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        setEmailError(email.trim() === '' ? 'Email required' : emailRegex.test(email) ? '' : 'Invalid email format.');

        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
        setPasswordError(password.trim() === '' ? 'Password required' : passwordRegex.test(password) ? '' : 'Password must be at least 8 characters, with one uppercase and one number');

        setConfirmPasswordError(confirmPassword.trim() === '' ? 'Confirm password required' : password === confirmPassword ? '' : 'Passwords do not match.');
    }

    return (
        <form>
            <Grid container spacing={2} style= {{margin: '20px'}}>
                <Grid>
                    <Item>
                        <h1>Create new account</h1>
                        <h3>Already a member? Log In</h3>
                    </Item>

                    <Item>
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
                    </Item>

                    <Item>
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
                    </Item>

                    <Item>
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
                    </Item>

                    <Item>
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
                    </Item>
                    <Item>
                        <Button onClick={handleClickButton} variant="contained" style = {{marginLeft: '150px'}}>Create account</Button>
                    </Item>
                </Grid>
            </Grid>
        </form>

    );
};

export default RegistrationForm;