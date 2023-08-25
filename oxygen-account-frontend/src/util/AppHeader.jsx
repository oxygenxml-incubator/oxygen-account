import * as React from 'react';
import { AppBar, Typography, Toolbar, Button, Grid } from "@mui/material";

/**
 * Header component displaying the application's logo and title.

 * @returns {JSX.Element} The JSX representation of the Header component.
 */
function AppHeader({ showLogoutButton }) {
    return (
        /* App Bar for Header */
        <AppBar position="static" style={{ backgroundColor: '#F0F0F0' }}>
            <Toolbar>
                <Grid container justifyContent="space-between">
                    <Grid item container wrap="nowrap" style={{ maxWidth: 'fit-content', alignItems: 'center' }}>
                        <Grid item>
                            {/* Logo */}
                            <img
                                src="/img/logo.jpg"
                                alt="Logo"
                                style={{
                                    width: 40,
                                    height: "auto",
                                    marginRight: "1rem",
                                }}
                            />
                        </Grid>
                        <Grid item>
                            {/* Title */}
                            <Typography
                                variant="h6"
                                align='center'
                                style={{ color: 'black' }}>
                                Oxygen Account
                            </Typography>
                        </Grid>
                    </Grid>

                    {showLogoutButton &&
                    <Grid item container style={{ maxWidth: 'fit-content' }}>
                        <form action="/logout" method="post">
                            <Button type="submit" variant="outlined" style={{ color: 'black', textTransform: 'none' }}>Log out</Button>
                        </form>
                    </Grid>}
                </Grid>
            </Toolbar>

        </AppBar>
    );
}
export default AppHeader;
