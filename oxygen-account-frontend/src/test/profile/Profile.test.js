import '@testing-library/jest-dom';
import { render, screen, waitFor } from '@testing-library/react';
import { setupServer } from 'msw/node'
import { rest } from 'msw';

import Profile from '../../profile/Profile.jsx';

// Create a mock server for handling API requests and responses
const server = setupServer();

// Run the mock server before all test cases.
beforeAll(() => server.listen())
// Reset request handlers after each individual test case.
afterEach(() => server.resetHandlers())
// Close the mock server after all test cases are finished.
afterAll(() => server.close())


/**
 * Test whether the profile component displays user data correctly.
 */
test('display data in profile component', async () => {
    // Mock a server response for user profile data
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com',
                        password: null
                    }
                )
            );
        })
    );

    // Render the Profile component
    render(<Profile />);

    // Check the presence of 'Profile' title
    expect(screen.getByText('Profile')).toBeInTheDocument();

    // Wait for the expected data to appear
    await waitFor(() => {
        expect(screen.getByText('Name:')).toBeInTheDocument();
        expect(screen.getByText('Marius Costescu')).toBeInTheDocument();
        expect(screen.getByText('Email:')).toBeInTheDocument();
        expect(screen.getByText('marius@yahoo.com')).toBeInTheDocument();
        expect(screen.getByText('MC')).toBeInTheDocument();
    });
});