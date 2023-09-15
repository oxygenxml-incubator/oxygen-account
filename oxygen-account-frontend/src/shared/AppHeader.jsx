import * as React from 'react';
import { AppBar, Typography, Toolbar, Button, Grid, Box, Tooltip, IconButton, Menu, MenuItem  } from "@mui/material";
import UserContext from "../profile/UserContext.jsx";
import OxygenAvatar from "./OxygenAvatar.jsx";
/**
 * Header component displaying the application's logo and title.

 * @returns {JSX.Element} The JSX representation of the Header component.
 */
function AppHeader({ showLogoutButton }) {
    const userContext = React.useContext(UserContext);
    const [anchorElUser, setAnchorElUser] = React.useState(null);
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
                        <Grid item container alignItems="center" style={{ maxWidth: 'fit-content' }}>
                            <Typography variant="body1" style={{ marginRight: '1rem', color: 'black' }}>
                                {currentUserData?.name || ""} 
                            </Typography>

                            <Box sx={{ flexGrow: 0 }}>
                                <Tooltip title="Open settings">
                                    <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                    <OxygenAvatar
                                         name={currentUserData?.name || ""}
                                        size={"50px"}
                                    />
                                    </IconButton>
                                </Tooltip>
                                <Menu
                                    sx={{ mt: '45px' }}
                                    id="menu-appbar"
                                    anchorEl={anchorElUser}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    open={Boolean(anchorElUser)}
                                    onClose={handleCloseUserMenu}
                                >
                                    <MenuItem onClick={handleCloseUserMenu}>
                                        <form action="/logout" method="post">
                                            <Button type="submit" style={{ color: 'black', textTransform: 'none' }}>Log out</Button>
                                        </form>
                                    </MenuItem>
                                </Menu>
                            </Box>
                        </Grid>
                    }
                </Grid>
            </Toolbar>

        </AppBar>
    );
}
export default AppHeader;
