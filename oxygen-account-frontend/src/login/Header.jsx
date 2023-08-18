import * as React from 'react';
import { AppBar, Typography, Toolbar, Container } from "@mui/material";

function Header() {
    const logoPathElement = document.querySelector('meta[name="logo-path"]');

    let path = logoPathElement.getAttribute('content');

    return (
        <AppBar position="static" style={{ backgroundColor: '#F0F0F0'}}>
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <img
                        src={path}
                        alt="Logo"
                        style={{
                            width: 40,
                            height: "auto",
                            marginRight: "1rem",
                            display: { xs: 'none', md: 'flex' }
                        }}
                    />
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
