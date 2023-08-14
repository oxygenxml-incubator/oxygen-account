import * as React from 'react';
import { AppBar, Typography, Toolbar, Container } from "@mui/material";
import logo_img from '../resources/logo/logo.jpg'

function Header() {
    return (
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <img
                        src={logo_img}
                        alt="Logo"
                        style={{
                            width: 40,
                            height: "auto",
                            marginRight: "1rem",
                            border: "1px solid #FFFFFF",
                            display: { xs: 'none', md: 'flex' }
                        }}
                    />
                    <Typography variant="h6" component="div">
                        OXYGEN ACCOUNT
                    </Typography>
                </Toolbar>
            </Container>
        </AppBar>
    );
}
export default Header;
