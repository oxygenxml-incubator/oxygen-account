import * as React from 'react';
import { AppBar, Typography, Toolbar, Container } from "@mui/material";

/**
 * Header component displaying the application's logo and title.

 * @returns {JSX.Element} The JSX representation of the Header component.
 */
function Header() {
    return (
        /* App Bar for Header */
        <AppBar position="static" style={{ backgroundColor: '#F0F0F0'}}>
            <Container maxWidth="xl">
                <Toolbar disableGutters>
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
                        variant="h6" 
                        component="div" 
                        style={{ color: 'black' }}>
                        Oxygen Account
                    </Typography>
                </Toolbar>
            </Container>
        </AppBar>
    );
}
export default Header;
