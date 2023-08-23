import * as React from 'react';
import { AppBar, Typography, Toolbar, Grid } from "@mui/material";

/**
 * Header component displaying the application's logo and title.

 * @returns {JSX.Element} The JSX representation of the Header component.
 */
function Header() {
    return (
        /* App Bar for Header */
        <AppBar position="static" style={{ backgroundColor: '#F0F0F0' }}>
            <Grid container style = {{marginLeft: "20px"}}>
                <Toolbar disableGutters >
                    {/* Logo */}
                    <Grid item>
                        <img
                            src="/img/logo.jpg"
                            alt="Logo"
                            style={{
                                width: 40,
                                height: "auto",
                                marginRight: "1rem",
                                display: { xs: 'none', md: 'flex' }
                            }}
                        />
                    </Grid>
                    {/* Title */}
                    <Grid item>
                        <Typography
                            variant="h6"
                            component="div"
                            style={{ color: 'black' }}>
                            Oxygen Account
                        </Typography>
                    </Grid>
                </Toolbar>
            </Grid>
        </AppBar>
    );
}
export default Header;
