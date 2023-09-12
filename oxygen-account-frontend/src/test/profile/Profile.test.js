import '@testing-library/jest-dom';
import { render, queryByAttribute, waitFor, fireEvent, screen } from '@testing-library/react';
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

function getDateTwoDaysAgo() {
    const currentDate = new Date();
    const NO_DAYS_AGO = 2;
    currentDate.setDate(currentDate.getDate() - NO_DAYS_AGO);

    return currentDate.toISOString();
}

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
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
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
    // Mock a server response for user profile data and a server response for saving the new name
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/profile', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: "Constantin-Marius Costescu",
                        email: "marius@yahoo.com",
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Edit name 
    await waitFor(() => {
        const editButton = getById(dom.container, 'edit-button');
        fireEvent.click(editButton);

        const nameInfo = getById(dom.container, 'name-info');
        fireEvent.change(nameInfo, { target: { value: 'Constantin-Marius Costescu' } });

        const saveButton = getById(dom.container, 'save-button');
        fireEvent.click(saveButton);
    });

    // Wait for the expected data to appear
    await waitFor(() => {
        const nameInfo = getById(dom.container, 'name-info');
        expect(nameInfo).toHaveValue('Constantin-Marius Costescu');

        const emailInfo = getById(dom.container, 'email-info');
        expect(emailInfo).toHaveValue('marius@yahoo.com');

        expect(screen.queryByText('Profile has been updated!')).toBeInTheDocument();
    });
});


/**
 * Test case to simulate edit error using an empty name.
 */
test('edit error empty name', async () => {
    // Mock a server response for user profile data and a server response for saving the new name
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/profile', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        internalErrorCode: 1008,
                        errorMessage: "Input validation failed.",
                        messageId: "INPUT_VALIDATION_FAILED",
                        errors: [
                            {
                                fieldName: "name",
                                errorMessage: "Please provide a non-empty value.",
                                messageId: "EMPTY_FIELD"
                            }
                        ]
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Edit name
    await waitFor(() => {
        const editButton = getById(dom.container, 'edit-button');
        fireEvent.click(editButton);

        const nameInfo = getById(dom.container, 'name-info');
        // Simulate valid data for frontend validation in order to check the response from server
        fireEvent.change(nameInfo, { target: { value: '-' } });

        const saveButton = getById(dom.container, 'save-button');

        fireEvent.click(saveButton);
    });

    // Wait for the expected error message to appear
    await waitFor(() => {
        expect(screen.queryByText('Please provide a non-empty value.')).toBeInTheDocument();
    });
});


/**
 * Test case to simulate changing password.
 */
test('change password', async () => {
    // Mock a server response for user profile data and a server response for saving the new password
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/password', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius',
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Change password
    await waitFor(() => {
        const changePasswordButton = getById(dom.container, 'change-password-button');
        fireEvent.click(changePasswordButton);

        // Fill current password field
        const currentPasswordField = getById(dom.container, 'current-password');
        fireEvent.change(currentPasswordField, { target: { value: '12345678' } });

        // Fill new password field
        const newPasswordField = getById(dom.container, 'new-password');
        fireEvent.change(newPasswordField, { target: { value: '123456789' } });

        // Confirm new password
        const confirmNewPasswordField = getById(dom.container, 'confirm-new-password');
        fireEvent.change(confirmNewPasswordField, { target: { value: '123456789' } });

        const savePasswordButton = getById(dom.container, 'save-password-button');
        fireEvent.click(savePasswordButton);
    });

    // Wait for the expected data to appear
    await waitFor(() => {
        expect(screen.getByText('The password has been changed successfully.')).toBeInTheDocument();
    });
});


/**
 * Test case to simulate error 'Incorrect password' when changing password.
 */
test('change password error - Incorrect password', async () => {
    // Mock a server response for user profile data and a server response for error 'Incorrect password'
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/password', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        internalErrorCode: 1010,
                        errorMessage: 'Incorrect password.',
                        messageId: 'INCORRECT_PASSWORD',
                        errors: null
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Change password
    await waitFor(() => {
        const changePasswordButton = getById(dom.container, 'change-password-button');
        fireEvent.click(changePasswordButton);

        // Fill current password field
        const currentPasswordField = getById(dom.container, 'current-password');
        fireEvent.change(currentPasswordField, { target: { value: '12345678' } });

        // Fill new password field
        const newPasswordField = getById(dom.container, 'new-password');
        fireEvent.change(newPasswordField, { target: { value: '123456789' } });

        // Confirm new password
        const confirmNewPasswordField = getById(dom.container, 'confirm-new-password');
        fireEvent.change(confirmNewPasswordField, { target: { value: '123456789' } });

        const savePasswordButton = getById(dom.container, 'save-password-button');
        fireEvent.click(savePasswordButton);
    });

    // Wait for the expected error to appear
    await waitFor(() => {
        expect(screen.getByText('Incorrect password.')).toBeInTheDocument();
    });
});


/**
 * Test case to simulate error 'password same as old' when changing password.
 */
test('change password error - password same as old', async () => {
    // Mock a server response for user profile data and a server response for error 'PASSWORD_SAME_AS_OLD'
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'marius@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/password', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        "internalErrorCode": 1011,
                        "errorMessage": "The new password is the same as the old one.",
                        "messageId": "PASSWORD_SAME_AS_OLD",
                        "errors": null
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Change password
    await waitFor(() => {
        const changePasswordButton = getById(dom.container, 'change-password-button');
        fireEvent.click(changePasswordButton);

        // Fill current password field
        const currentPasswordField = getById(dom.container, 'current-password');
        fireEvent.change(currentPasswordField, { target: { value: '123456789' } });

        // Fill new password field
        const newPasswordField = getById(dom.container, 'new-password');
        fireEvent.change(newPasswordField, { target: { value: '123456789' } });

        // Fill current password field
        const confirmNewPasswordField = getById(dom.container, 'confirm-new-password');
        fireEvent.change(confirmNewPasswordField, { target: { value: '123456789' } });

        const savePasswordButton = getById(dom.container, 'save-password-button');
        fireEvent.click(savePasswordButton);
    });

    // Wait for the expected error to appear
    await waitFor(() => {
        expect(screen.getByText('The new password is the same as the old one.')).toBeInTheDocument();
    });
});


/**
 * Test case to simulate account deletion.
 */
test('delete account', async () => {
    // Mock a server response for user profile data and a server response for account deletion
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'mariuscostescu@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/delete', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'mariuscostescu@yahoo.com',
                        password: null,
                        status: 'deleted',
                        deletionDate: getDateTwoDaysAgo()
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Simulate the process of deleting the account
    await waitFor(() => {
        // Click Delete Account button
        const deleteAccountButton = getById(dom.container, 'delete-account-button');
        fireEvent.click(deleteAccountButton);

        // Insert password to confirm deletion
        const deletePasswordFiled = screen.getByLabelText('Password');
        fireEvent.change(deletePasswordFiled, { target: { value: 'password' } });

        // Confirm deletion
        const confirmDeleteAccountButton = screen.getByRole('button', { name: 'Delete account' });
        fireEvent.click(confirmDeleteAccountButton);
    });

    // Wait for 'Recover account' section to appear.
    await waitFor(() => {
        expect(screen.getByText('Recover account')).toBeInTheDocument();
        expect(screen.getByText('Your account is marked as deleted. It is scheduled to be permanently deleted in 5 days.')).toBeInTheDocument();
    })
});


/**
 * Test case to simulate the process of recovering a deleted user account.
 */
test('recover account', async () => {
    // Mock a server response for user profile data and a server response for account recovery
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'mariuscostescu@yahoo.com',
                        password: null,
                        status: 'deleted',
                        deletionDate: getDateTwoDaysAgo()
                    }
                )
            );
        }),
        rest.put('/api/users/recover', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'mariuscostescu@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Simulate the process of recovering the account
    await waitFor(() => {
        // Click Recover Account button
        const recoverAccountButton = getById(dom.container, 'recover-account-button');
        fireEvent.click(recoverAccountButton);
    });

    // Wait for 'Delete account' section to appear.
    await waitFor(() => {
        expect(screen.getByText('Delete account')).toBeInTheDocument();
    })
});


/**
 * Test case to simulate an error when deleting the account with an incorrect password.
 */
test('delete account error - INCORRECT PASSWORD', async () => {
    // Mock a server response for user profile data and a server response for the INCORRECT_PASSWORD error
    server.use(
        rest.get('/api/users/me', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        name: 'Marius Costescu',
                        email: 'mariuscostescu@yahoo.com',
                        password: null,
                        status: 'active',
                        deletionDate: null
                    }
                )
            );
        }),
        rest.put('/api/users/delete', async (req, res, ctx) => {
            return res(
                ctx.json(
                    {
                        internalErrorCode: 1010,
                        errorMessage: "Incorrect password.",
                        messageId: "INCORRECT_PASSWORD",
                        errors: null
                    }
                ),
                ctx.status(403)
            );
        })
    );

    // Render the Profile component
    const dom = render(<Profile />);

    // Simulate the process of deleting the account with an incorrect password
    await waitFor(() => {
        // Click Delete Account button
        const deleteAccountButton = getById(dom.container, 'delete-account-button');
        fireEvent.click(deleteAccountButton);

        // Insert an incorrect password
        const deletePasswordFiled = screen.getByLabelText('Password');
        fireEvent.change(deletePasswordFiled, { target: { value: 'passwordd' } });

        // Confirm deletion
        const confirmDeleteAccountButton = screen.getByRole('button', { name: 'Delete account' });
        fireEvent.click(confirmDeleteAccountButton);
    });

    // Wait for the expected error message to appear
    await waitFor(() => {
        expect(screen.getByText('Incorrect password.')).toBeInTheDocument();
    });

});