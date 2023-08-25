import React, { useState, useEffect} from 'react';
import { Grid, Card, CardHeader, CardContent, LinearProgress, Typography, Avatar } from '@mui/material';

/**
 * Component responsible for rendering the profile card containing current user info.
 * 
 * @returns {JSX.Element} The JSX representation of the ProfileCard component.
 */
const stringToColor = (string) => {
    let hash = 0;
    let i;

    for (i = 0; i < string.length; i += 1) {
      hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }
  
    let color = '#';
  
    for (i = 0; i < 3; i += 1) {
      const value = (hash >> (i * 8)) & 0xff;
      color += `00${value.toString(16)}`.slice(-2);
    }
  
    return color;
  }

  const stringAvatar = (name) => {
    const names = name.split(' ');
  
    return {
        sx: {
            bgcolor: stringToColor(name),
        },
        children: `${name.split(' ')[0][0] || ''}${name.split(' ')[1] ? name.split(' ')[1][0] : ''}`,
    };
};

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
                            <Grid container >
                                <Grid item style={{ flex: '0 0 60px'}}> 
                                {userData && <Avatar {...stringAvatar(userData.name)} />}
                                </Grid>

                                <Grid item style={{ flex: '1' }} container direction="column">
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