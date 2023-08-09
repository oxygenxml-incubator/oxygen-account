package com.oxygenxml.account.user;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.containsInAnyOrder;


import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.utility.JsonUtil;
import com.oxygenxml.account.messages.Message;

/**
 * The UserControllerTest class tests the functionality of UserController
 *
 */
@SpringBootTest(classes=OxygenAccountApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(locations="classpath:application-test.properties")
@SqlGroup({
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:db/ClearDatabase.sql", "classpath:db/PopulateDatabase.sql"}),
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/ClearDatabase.sql")
})
public class UserControllerTest {

	/**
	 * The MockMvc instance is used for simulating HTPP requests
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * The testRegisterUser method tests the user registration functionality.
	 *  It attempts to register a new user
	 * @throws Exception if the test encounters any errors.
	 */
	@Test
	void testRegisterUser() throws Exception {
		UserDto newUser = new UserDto();

		newUser.setName("test");
		newUser.setEmail("teesting@test.com");
		newUser.setPassword("testtesttest");

		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUser)));
		resultAction.andExpect(status().isOk());
	}

	/**
	 * The testRegisterSameEmail method tests whether another user with the same email can be registered
	 * It registers an initial user, then attempts to register a new user with the same email.
	 * @throws Exception if the test encounters any errors
	 */
	@Test
	void testRegisterSameEmail() throws Exception{
		UserDto newUser = new UserDto();

		newUser.setName("Test");
		newUser.setEmail("test@gmail.com");
		newUser.setPassword("password");

		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUser)));
		resultAction.andExpect(status().isConflict())
		.andExpect(jsonPath("$.errorMessage", is(Message.EMAIL_ALREADY_EXISTS.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1001)))
		.andExpect(jsonPath("$.messageId", is(Message.EMAIL_ALREADY_EXISTS.getId())));
	}

	/**
	 * The testInvalidEmail method tests the registration with an invalid email.
	 * @throws Exception
	 */
	@Test
	void testInvalidEmail() throws Exception {
		UserDto newUserDto = new UserDto();
		newUserDto.setName("Test");
		newUserDto.setEmail("invalidemail");
		newUserDto.setPassword("password");

		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUserDto)));

		resultAction.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.errors[0].errorMessage", is(Message.EMAIL_INVALID.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[0].messageId", is(Message.EMAIL_INVALID.getId())));

	}

	/**
	 * The testInvalidPassword tests the registration with an invalid password.
	 * @throws Exception
	 */
	@Test
	void testInvalidPassword() throws Exception{
		UserDto newUserDto = new UserDto();
		newUserDto.setName("Test");
		newUserDto.setEmail("email@email.com");
		newUserDto.setPassword("pass");

		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUserDto)));

		resultAction.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.errors[0].errorMessage", is(Message.SHORT_FIELD.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[0].messageId", is(Message.SHORT_FIELD.getId())));

	}

	/**
	 * The testEmptyField tests the registration with an invalid password.
	 * @throws Exception
	 */
	@Test
	void testEmptyField() throws Exception{
		UserDto newUserDto = new UserDto();
		newUserDto.setName("");
		newUserDto.setEmail("email@email.com");
		newUserDto.setPassword("password");

		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUserDto)));

		resultAction.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.errors[0].errorMessage", is(Message.EMPTY_FIELD.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[0].messageId", is(Message.EMPTY_FIELD.getId())));

	}

	/**
	 * The testMultipleErrors tests that the MultipleOxygenAccountException is throwen correctly
	 * @throws Exception
	 */
	@Test
	void testMultipleErrors() throws Exception{
		UserDto newUserDto = new UserDto();
		newUserDto.setName("");
		newUserDto.setEmail("emailemail.com");
		newUserDto.setPassword("pass");

		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUserDto)));

		resultAction.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[*].errorMessage", containsInAnyOrder(Message.EMPTY_FIELD.getMessage(), Message.SHORT_FIELD.getMessage(), Message.EMAIL_INVALID.getMessage())))
		.andExpect(jsonPath("$.errors[*].messageId", containsInAnyOrder(Message.EMPTY_FIELD.getId(), Message.SHORT_FIELD.getId(), Message.EMAIL_INVALID.getId())));
	}
}

