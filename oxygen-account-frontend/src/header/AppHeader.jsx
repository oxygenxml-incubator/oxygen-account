import * as React from 'react';
import { AppBar, Typography, Toolbar, Button } from "@mui/material";

/**
 * Header component displaying the application's logo and title.

 * @returns {JSX.Element} The JSX representation of the Header component.
 */
function AppHeader() {
    return (
        /* App Bar for Header */
        <AppBar position="static" style={{ backgroundColor: '#F0F0F0' }}>

            <Toolbar>
                {/* Logo */}
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
                {/* Title */}
                <Typography 
                    sx={{ flexGrow: 1 }}
                    variant="h6"
                    component="div"
                    style={{ color: 'black' }}>
                    Oxygen Account
                </Typography>

                <form action="/logout" method="post">
                    <Button type="submit" color="inherit" style={{ color: 'black' }}>Log out</Button>
                </form>
                
            </Toolbar>

        </AppBar>
    );
}
export default AppHeader;
