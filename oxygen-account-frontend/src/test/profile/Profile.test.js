import '@testing-library/jest-dom';
import { render, queryByAttribute, waitFor, fireEvent } from '@testing-library/react';
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

const getById = queryByAttribute.bind(null, 'id');

/**
 * Test case to verify if the profile component displays user data correctly.
 */
test('display data in profile component', async () => {
    // Mock a server response for user profile data
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com'
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Wait for the expected data to appear
    await waitFor(() => {
        const nameInfo = getById(dom.container, 'name-info');
        expect(nameInfo).toHaveValue('Marius Costescu');

        const emailInfo = getById(dom.container, 'email-info');
        expect(emailInfo).toHaveValue('marius@yahoo.com');
    });
});


/**
 * Test case to simulate changing name.
 */
test('edit name', async () => {
    // Mock a server response for user profile data
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com'
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Mock a server response for saving the new name
    server.use(
        rest.get('/api/users/profile', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: "Constantin-Marius Costescu",
                        email: "marius@yahoo.com",
                        password: null
                    }
                )
            );
        })
    );

    // Edit name and Wait for the expected data to appear
    await waitFor(() => {
        const editButton = getById(dom.container, 'edit-button');
        fireEvent.click(editButton);

        const nameInfo = getById(dom.container, 'name-info');
        fireEvent.change(nameInfo, { target: { value: 'Constantin-Marius Costescu' } });

        const saveButton = getById(dom.container, 'save-button');
        fireEvent.click(saveButton);

        expect(nameInfo).toHaveValue('Constantin-Marius Costescu');

        const emailInfo = getById(dom.container, 'email-info');
        expect(emailInfo).toHaveValue('marius@yahoo.com');
    });
});