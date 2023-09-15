import React from "react";

import { Avatar } from "@mui/material";


/**
 * Generates an avatar with a color and initials based on a given name.
 *
 * @param {string} name - The name used to generate the avatar color and initials.
 * @returns {JSX.Element} The JSX representation of the OxygenAvatar component.
 */
function OxygenAvatar( {name, size} ) {
    /**
     * Converts a string to a color code.
     *
     * @param {string} string - The input string.
     * @returns {string} The color code generated from the input string.
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
    
    /**
     * Generates an object with color and initials for the avatar.
     *
     * @param {string} name - The name used to generate the initials.
     * @returns {Object} The object for the avatar.
     */
    const stringAvatar = (name) => {
        return `${name.split(' ')[0][0] || ''}${name.split(' ')[1] ? name.split(' ')[1][0] : ''}`;
    };

    return (
        // Render the Avatar component
        <Avatar
            children = {stringAvatar(name)}
            sx= {{bgcolor: stringToColor(name), 
                width: size, 
                height: size, 
                fontSize: `${parseInt(size) / 2.5}px`
            }}
        />
    );
}

export default OxygenAvatar;