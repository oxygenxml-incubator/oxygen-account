import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { setupServer } from 'msw/node'
import { rest } from 'msw';

import AuthContainer from '../../login/AuthContainer';

// Create a mock server for handling API requests and responses
const server = setupServer();

// Run the mock server before all test cases.
beforeAll(() => server.listen())
// Reset request handlers after each individual test case.
afterEach(() => server.resetHandlers())
// Close the mock server after all test cases are finished.
afterAll(() => server.close())

/**
 * Test case to validate the display of error messages for invalid input data.
 */
test('displays error messages for invalid data', () => {
  render(<AuthContainer />);

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
  expect(screen.getByText('Invalid name.')).toBeInTheDocument();
  expect(screen.getByText('Invalid email.')).toBeInTheDocument();
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
        })
      );
    })
  );

  render(<AuthContainer />);

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
      expect(screen.getByText('Account created successfully!')).toBeInTheDocument();
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
        })
      );
    })
  );
  render(<AuthContainer />);

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
test('network error', async () => {
  // Mock a network error response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res.networkError();
    })
  );

  render(<AuthContainer />);

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
 * Test case that simulates pressing the close button on the Snackbar.
 */
test('closes Snackbar when close button is clicked', async () => {
  // Mock a successful registration response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          name: 'Costescu Constantin-Marius',
          email: 'costescumaryus558@yahoo.com',
          password: null,
        })
      );
    })
  );

  render(<AuthContainer />);

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
      expect(screen.getByText('Account created successfully!')).toBeInTheDocument();
  });

  // Click the close button on the Snackbar
  fireEvent.click(screen.getByRole('button', { name: /close/i }));

  // Wait for the success message to disappear
  await waitFor(() => {
    expect(screen.queryByText('Account created successfully!')).not.toBeInTheDocument();
  });
});



/**
 * Test case to verify that input fields are reset after successful form submission.
 */
test('input fields are reset after successful submission', async () => {
  // Mock a successful registration response
  server.use(
    rest.post('/api/users/register', async (req, res, ctx) => {
      return res(
        ctx.json({
          name: 'Costescu Constantin-Marius',
          email: 'costescumaryus558@yahoo.com',
          password: null,
        })
      );
    })
  );

  render(<AuthContainer />);

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
      expect(screen.getByText('Account created successfully!')).toBeInTheDocument();
  });

  // Verify that input fields are reset after successful submission
  expect(screen.getByLabelText('Name')).toHaveValue('');
  expect(screen.getByLabelText('Email')).toHaveValue('');
  expect(screen.getByLabelText('Password')).toHaveValue('');
  expect(screen.getByLabelText('Confirm Password')).toHaveValue('');
});
