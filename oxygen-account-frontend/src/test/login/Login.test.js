import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { setupServer } from 'msw/node'
import { rest } from 'msw';

import AuthContainer from '../../login/AuthContainer.jsx';

// Create a mock server for handling API requests and responses
const server = setupServer();

// Run the mock server before all test cases.
beforeAll(() => server.listen())
// Reset request handlers after each individual test case.
afterEach(() => server.resetHandlers())
// Close the mock server after all test cases are finished.
afterAll(() => server.close())

/**
 * Test case to validate the display of error messages for invalid input data in register form.
 */
test('displays error messages for invalid data in register form', () => {
  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate invalid data
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: '' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'marius@yahoo' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: '1234567' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: '7654321' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Verify the presence of expected error messages
  expect(screen.getByText('Please provide a non-empty value.')).toBeInTheDocument();
  expect(screen.getByText('Email should be valid.')).toBeInTheDocument();
  expect(screen.getByText('Password must be at least 8 characters.')).toBeInTheDocument();
  expect(screen.getByText('Passwords do not match.')).toBeInTheDocument();
});


/**
 * Test case to simulate sending a registration request with valid input data.
 */
test('sends registration request for valid data', async () => {
  // Mock a successful registration response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          name: 'Costescu Constantin-Marius',
          email: 'costescumaryus558@yahoo.com',
          password: null,
          status: 'active',
          deletionDate: null
        })
      );
    })
  );

  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate valid data
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: 'Costescu Constantin-Marius' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'costescumaryus558@yahoo.com' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: 'Costescu223' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: 'Costescu223' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Wait for the success message to appear
  await waitFor(() => {
    expect(screen.getByText('A confirmation email has been sent to your address.')).toBeInTheDocument();
  });
});


/**
 * Test case to simulate sending a registration request with an already used email.
 */
test('sends registration request with used email', async () => {
  // Mock a response indicating that the email is already in use
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          internalErrorCode: 1,
          errorMessage: 'User with this email already exists.',
          messageId: 'EMAIL_ALREADY_EXISTS',
          errors: null
        })
      );
    })
  );

  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate valid data (with already used email)
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: 'Costescu Constantin-Marius' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'costescumaryus558@yahoo.com' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: 'Costescu223' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: 'Costescu223' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Wait for the error message to appear
  await waitFor(() => {
    expect(screen.getByText('User with this email already exists.')).toBeInTheDocument();
  });
});


/**
 * Test case to simulate a network error during the registration request.
 */
test('network error on register submit', async () => {
  // Mock a network error response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res.networkError();
    })
  );

  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate valid data
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: 'Costescu Constantin-Marius' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'costescumaryus558@yahoo.com' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: 'Costescu223' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: 'Costescu223' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Wait for the error message to appear
  await waitFor(() => {
    expect(screen.getByText('The connection could not be established.')).toBeInTheDocument();
  });
});


/**
 * Test case that simulates pressing the close button on the Snackbar from register page.
 */
test('closes Snackbar from register page when close button is clicked', async () => {
  // Mock a successful registration response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          name: 'Costescu Constantin-Marius',
          email: 'costescumaryus558@yahoo.com',
          password: null,
          status: 'active',
          deletionDate: null
        })
      );
    })
  );

  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate valid data
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: 'Costescu Constantin-Marius' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'costescumaryus558@yahoo.com' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: 'Costescu223' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: 'Costescu223' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Wait for the success message to appear
  await waitFor(() => {
    expect(screen.getByText('A confirmation email has been sent to your address.')).toBeInTheDocument();
  });

  // Click the close button on the Snackbar
  fireEvent.click(screen.getByRole('button', { name: /close/i }));

  // Wait for the success message to disappear
  await waitFor(() => {
    expect(screen.queryByText('A confirmation email has been sent to your address.')).not.toBeInTheDocument();
  });
});



/**
 * Test case to verify that input fields are reset after successful register form submission.
 */
test('input fields are reset after successful submission of register form', async () => {
  // Mock a successful registration response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          name: 'Costescu Constantin-Marius',
          email: 'costescumaryus558@yahoo.com',
          password: null,
          status: 'active',
          deletionDate: null
        })
      );
    })
  );

  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate valid data
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: 'Costescu Constantin-Marius' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'costescumaryus558@yahoo.com' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: 'Costescu223' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: 'Costescu223' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Wait for the success message to appear
  await waitFor(() => {
    expect(screen.getByText('A confirmation email has been sent to your address.')).toBeInTheDocument();
  });

  // Verify that input fields are reset after successful submission
  expect(screen.getByLabelText('Name')).toHaveValue('');
  expect(screen.getByLabelText('Email')).toHaveValue('');
  expect(screen.getByLabelText('Password')).toHaveValue('');
  expect(screen.getByLabelText('Confirm Password')).toHaveValue('');
});


/**
 * Test case to verify that the server send the correct errors for validation
 */
test('handle input errors from server for sign up form', async () => {
  // Mock a errors list response from server
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          internalErrorCode: 1008,
          errorMessage: 'Input validation failed.',
          messageId: 'INPUT_VALIDATION_FAILED',
          errors: [
              {
                  fieldName: 'password',
                  errorMessage: 'Input field is too short. Please enter a longer value.',
                  messageId: 'SHORT_FIELD'
              },
              {
                  fieldName: 'name',
                  errorMessage: 'Please provide a non-empty value.',
                  messageId: 'EMPTY_FIELD'
              },
              {
                  fieldName: 'email',
                  errorMessage: 'Email should be valid.',
                  messageId: 'INVALID_EMAIL'
              }
          ]
        })
      );
    })
  );

  render(<AuthContainer />);

  // Switch to register form
  fireEvent.click(screen.getByText('Create an account'));

  // Get input elements and simulate valid data for frontend validation in order to check the response from server
  const inputName = screen.getByLabelText('Name');
  fireEvent.change(inputName, { target: { value: 'Marius Costescu' } });

  const inputEmail = screen.getByLabelText('Email');
  fireEvent.change(inputEmail, { target: { value: 'costescumaryus558@yahoo.com' } });

  const inputPassword = screen.getByLabelText('Password');
  fireEvent.change(inputPassword, { target: { value: '12345678' } });

  const inputConfirmPassword = screen.getByLabelText('Confirm Password');
  fireEvent.change(inputConfirmPassword, { target: { value: '12345678' } });

  // Click the "Create account" button
  fireEvent.click(screen.getByText('Create account'));

  // Verify that the errors message are associate with the fields
  await waitFor(() => {
    expect(screen.getByText('Please provide a non-empty value.')).toBeInTheDocument();
    expect(screen.getByText('Email should be valid.')).toBeInTheDocument();
    expect(screen.getByText('Input field is too short. Please enter a longer value.')).toBeInTheDocument();
  });
});


/**
 * Test case to validate the display of error messages for invalid input data in login form.
 */
test('displays error messages for invalid data in login form', () => {
  render(<AuthContainer />);

  // Get input elements and simulate invalid data
  const inputName = screen.getByLabelText('Email');
  fireEvent.change(inputName, { target: { value: '' } });

  const inputEmail = screen.getByLabelText('Password');
  fireEvent.change(inputEmail, { target: { value: '' } });

  // Click the "Log In" button
  fireEvent.click(screen.getByRole('button', { name: 'Log In' }));

  // Verify the presence of expected error messages
  expect(screen.getByText('Empty email.')).toBeInTheDocument();
  expect(screen.getByText('Empty password.')).toBeInTheDocument();
});
