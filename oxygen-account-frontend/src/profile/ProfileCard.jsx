import React, { useState, useEffect } from 'react';
import { Grid, Card, CardHeader, CardContent, LinearProgress, Typography, Avatar } from '@mui/material';

/**
 * Component responsible for rendering the profile card containing current user info.
 * 
 * @returns {JSX.Element} The JSX representation of the ProfileCard component.
 */
function ProfileCard() {
    const [userData, setUserData] = useState(null);
    const [isLoadingActive, setIsLoadingActive] = useState(true);

    useEffect(() => {
        setIsLoadingActive(true);

        fetch('api/users/me', {
            method: 'GET'
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                setUserData(data);

                setIsLoadingActive(false);
            })
    }, []);

    return (
        // Main container for the profile card
        <Grid container justifyContent="center" alignItems="center">
            <Grid item xs={12} md={9} lg={5} xl={5} style={{ marginTop: 50 }}>
                <Card>
                    {/* Header for displaying title*/}
                    <CardHeader
                        title={"Profile"}
                    />

                    <CardContent>
                        {isLoadingActive ? (
                            <LinearProgress />
                        ) : (
                            <Grid container>
                                <Grid item>
                                    asd
                                </Grid>

                                <Grid item container direction="column">
                                    <Grid item>
                                        <Typography> {userData.name} </Typography>
                                    </Grid>
                                    <Grid item>
                                        <Typography> {userData.email} </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>

                        )}
                    </CardContent>
                </Card>
            </Grid>
        </Grid>
    );
}

export default ProfileCard;