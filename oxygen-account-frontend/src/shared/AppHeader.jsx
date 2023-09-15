import * as React from 'react';
import { AppBar, Typography, Toolbar, Button, Grid, Box, Tooltip, IconButton, Menu, MenuItem } from "@mui/material";
import UserContext from "../profile/UserContext.jsx";
import OxygenAvatar from "./OxygenAvatar.jsx";

import { useState, useContext } from 'react';
/**
 * Header component displaying the application's logo and title.

 * @returns {JSX.Element} The JSX representation of the Header component.
 */
function AppHeader({ showLogoutButton }) {
    const [anchorElUser, setAnchorElUser] = useState(null);

    const userContext = useContext(UserContext);
    const currentUserData = userContext ? userContext.currentUserData : null;

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    return (
        /* App Bar for Header */
        <AppBar position="static" style={{ backgroundColor: '#F0F0F0' }}>
            <Toolbar>
                <Grid container justifyContent="space-between">
                    <Grid item container wrap="nowrap" style={{ maxWidth: 'fit-content', alignItems: 'center' }}>
                        {/* Logo */}
                        <Grid item>
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

                        {/* Title */}
                        <Grid item>
                            <Typography
                                variant="h6"
                                align='center'
                                style={{ color: 'black' }}>
                                Oxygen Account
                            </Typography>
                        </Grid>
                    </Grid>

                    {showLogoutButton && currentUserData &&
                        <Grid item container style={{ maxWidth: 'fit-content' }}>
                            <Grid item>
                                <Tooltip title="Open menu">
                                    <IconButton onClick={handleOpenUserMenu} sx = {{borderRadius: '0px'}}>
                                        <Grid container alignItems="center">
                                            <Grid item>
                                                <Typography variant="body1" style={{ marginRight: '1rem', color: 'black' }}>
                                                    {currentUserData.name + " \u25BE"}
                                                 </Typography>
                                            </Grid>
                                            <Grid item>
                                                <OxygenAvatar
                                                    name={currentUserData.name}
                                                    size={"50px"}
                                                />
                                            </Grid>
                                        </Grid>
                                    </IconButton>
                                </Tooltip>
                            </Grid>
                            <Grid item>
                                <Menu
                                    MenuListProps={{ sx: { padding: '0px' } }}
                                    id="menu"
                                    anchorEl={anchorElUser}
                                    anchorOrigin={{
                                        vertical: 'bottom',
                                        horizontal: 'right',
                                    }}
                                    keepMounted

                                    open={Boolean(anchorElUser)}
                                    onClose={handleCloseUserMenu}
                                >
                                    <MenuItem onClick={handleCloseUserMenu}>
                                        <form action="/logout" method="post">
                                            <Button type="submit" style={{ color: 'black', textTransform: 'none' }}>Log out</Button>
                                        </form>
                                    </MenuItem>
                                </Menu>
                            </Grid>
                        </Grid>
                    }
                </Grid>
            </Toolbar>

        </AppBar>
    );
}
export default AppHeader;
