package com.oxygenxml.account.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.dto.ChangePasswordDto;
import com.oxygenxml.account.dto.UpdateUserNameDto;
import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.messages.Message;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.service.UserService;
import com.oxygenxml.account.utility.JsonUtil;

/**
 * UserControllerTest class tests the functionality of UserController
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
	 * MockMvc instance is used for simulating HTPP requests
	 */
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	/**
     * The password encoder that will be used to encode passwords
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

	/**
	 *  Tests the user registration functionality.
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
	 * Tests whether another user with the same email can be registered
	 * It registers an initial user, then attempts to register a new user with the same email.
	 * @throws Exception if the test encounters any errors
	 */
	@Test
	void testRegisterSameEmail() throws Exception{
		UserDto newUser = new UserDto();

		newUser.setName("Denis Mateescu");
		newUser.setEmail("denismateescu@gmail.com");
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
	 * Tests the registration with an invalid email.
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
		.andExpect(jsonPath("$.errors[0].fieldName", is("email")))
		.andExpect(jsonPath("$.errors[0].errorMessage", is(Message.EMAIL_INVALID.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[0].messageId", is(Message.EMAIL_INVALID.getId())));

	}

	/**
	 * Tests the registration with an invalid password.
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
		.andExpect(jsonPath("$.errors[0].fieldName", is("password")))
		.andExpect(jsonPath("$.errors[0].errorMessage", is(Message.SHORT_FIELD.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[0].messageId", is(Message.SHORT_FIELD.getId())));

	}

	/**
	 * Tests the registration with an invalid password.
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
		.andExpect(jsonPath("$.errors[0].fieldName", is("name")))
		.andExpect(jsonPath("$.errors[0].errorMessage", is(Message.EMPTY_FIELD.getMessage())))
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[0].messageId", is(Message.EMPTY_FIELD.getId())));

	}

	/**
	 * Tests that the MultipleOxygenAccountException is throwen correctly
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
		.andExpect(jsonPath("$.errors[?(@.fieldName == 'name')].errorMessage", hasItem(Message.EMPTY_FIELD.getMessage())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'name')].messageId", hasItem(Message.EMPTY_FIELD.getId())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'email')].errorMessage", hasItem(Message.EMAIL_INVALID.getMessage())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'email')].messageId", hasItem(Message.EMAIL_INVALID.getId())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'password')].errorMessage", hasItem(Message.SHORT_FIELD.getMessage())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'password')].messageId", hasItem(Message.SHORT_FIELD.getId())));
	}
	
	/**
	 * Tests whether the user's name and email are displayed after logging in
	 * @throws Exception
	 */
	@Test
	void testShowDetailsAboutUser() throws Exception {
		UserDto newUserDto = new UserDto();
		newUserDto.setName("User");
		newUserDto.setEmail("test@email.com");
		newUserDto.setPassword("password");

		mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUserDto)));

		ResultActions sessionAccess = mockMvc.perform(get("/profile"))
				.andExpect(status().isFound()) 
				.andExpect(redirectedUrlPattern("**/login"));

		MvcResult result = sessionAccess.andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();

		mockMvc.perform(post("/login").session(session)
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "test@email.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrlPattern("**/profile?continue"));

		mockMvc.perform(get("/api/users/me").session(session))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is("User")))
		.andExpect(jsonPath("$.email", is("test@email.com")));
	}

	/**
	 * Tests what is displayed to an unknown user who accesses this area of ​​the application
	 * @throws Exception
	 */
	@Test
	void testAnonymousUserDetails() throws Exception {

		mockMvc.perform(get("/api/users/me"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is("Anonymous User")))
		.andExpect(jsonPath("$.email", is("anonymousUser")));
	}
	
	/**
	 * Tests if a User can change his name
	 * @throws Exception
	 */
	@Test
	void testChangeName() throws Exception {
		MvcResult result = mockMvc.perform(post("/login")
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "denismateescu@gmail.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andReturn();
		
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		
		UpdateUserNameDto updateNameDto = new UpdateUserNameDto();
		updateNameDto.setName("Marius Costescu");
		
		mockMvc.perform(put("/api/users/profile").session(session)
				.contentType("application/json")
				.content(JsonUtil.asJsonString(updateNameDto)))
		.andExpect(status().isOk());
		
		mockMvc.perform(get("/api/users/me").session(session))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is("Marius Costescu")))
		.andExpect(jsonPath("$.email", is("denismateescu@gmail.com")));
		
		User user = userService.getUserByEmail("denismateescu@gmail.com");
		
		assertThat(user.getName(), equalTo("Marius Costescu"));
	}
	
	/**
	 * Tests the situation in which a User enters an empty name
	 * @throws Exception
	 */
	@Test
	void testChangeEmptyName() throws Exception {
		MvcResult result = mockMvc.perform(post("/login")
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "denismateescu@gmail.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andReturn();
		
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		
		UpdateUserNameDto updateNameDto = new UpdateUserNameDto();
		updateNameDto.setName("");
		
		mockMvc.perform(put("/api/users/profile").session(session)
				.contentType("application/json")
				.content(JsonUtil.asJsonString(updateNameDto)))
		.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[?(@.fieldName == 'name')].errorMessage", hasItem(Message.EMPTY_FIELD.getMessage())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'name')].messageId", hasItem(Message.EMPTY_FIELD.getId())));
	}
	
	/**
	 * Tests the situation when a user want to change his password
	 * @throws Exception
	 */
	@Test
	void testChangePassword() throws Exception {
		MvcResult result = mockMvc.perform(post("/login")
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "denismateescu@gmail.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andReturn();
		
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		
		ChangePasswordDto changePassword = new ChangePasswordDto();
		changePassword.setOldPassword("password");
		changePassword.setNewPassword("password1234");
		
		mockMvc.perform(put("/api/users/password").session(session)
				.contentType("application/json")
				.content(JsonUtil.asJsonString(changePassword)))
		.andExpect(status().isOk());
		
		User user = userService.getUserByEmail("denismateescu@gmail.com");
		
		assertTrue(passwordEncoder.matches("password1234", user.getPassword()));
	}
	
	/**
	 * Tests the situation when a user inserts a wrong password
	 * @throws Exception
	 */
	@Test
	void testChangePasswordIncorrectOldPassword() throws Exception {
		MvcResult result = mockMvc.perform(post("/login")
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "denismateescu@gmail.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andReturn();
		
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		
		ChangePasswordDto changePassword = new ChangePasswordDto();
		changePassword.setOldPassword("wrongPassword");
		changePassword.setNewPassword("password1234");
		
		mockMvc.perform(put("/api/users/password").session(session)
				.contentType("application/json")
				.content(JsonUtil.asJsonString(changePassword)))
		.andExpect(status().isForbidden())
		.andExpect(jsonPath("$.errorMessage", is(Message.INCORRECT_PASSWORD.getMessage())));;
		
		
		 User user = userService.getUserByEmail("denismateescu@gmail.com");
		
		 assertTrue(passwordEncoder.matches("password", user.getPassword()));
	}
	
	/**
	 * Tests the situation when a user inserts the new password the same as the old password
	 * @throws Exception
	 */
	@Test
	void testSameNewAndOldPasswords() throws Exception {
		MvcResult result = mockMvc.perform(post("/login")
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "denismateescu@gmail.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andReturn();
		
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		
		ChangePasswordDto changePassword = new ChangePasswordDto();
		changePassword.setOldPassword("password");
		changePassword.setNewPassword("password");
		
		mockMvc.perform(put("/api/users/password").session(session)
				.contentType("application/json")
				.content(JsonUtil.asJsonString(changePassword)))
		.andExpect(status().isForbidden())
		.andExpect(jsonPath("$.errorMessage", is(Message.PASSWORD_SAME_AS_OLD.getMessage())));;
		
		
		 User user = userService.getUserByEmail("denismateescu@gmail.com");
		
		 assertTrue(passwordEncoder.matches("password", user.getPassword()));
	}
	
	/**
	 * Tests the situation when a user left empty the old password field and inserts a short new password
	 * @throws Exception
	 */
	@Test
	void testValidatePasswords() throws Exception {
		MvcResult result = mockMvc.perform(post("/login")
				.contentType(APPLICATION_FORM_URLENCODED)
				.param("email", "denismateescu@gmail.com")
				.param("password", "password"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andReturn();
		
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		
		ChangePasswordDto changePassword = new ChangePasswordDto();
		changePassword.setOldPassword("");
		changePassword.setNewPassword("pass");
		
		mockMvc.perform(put("/api/users/password").session(session)
				.contentType("application/json")
				.content(JsonUtil.asJsonString(changePassword)))
		.andExpect(status().isUnprocessableEntity())
		.andExpect(jsonPath("$.internalErrorCode", is(1008)))
		.andExpect(jsonPath("$.errors[?(@.fieldName == 'oldPassword')].errorMessage", hasItem(Message.EMPTY_FIELD.getMessage())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'oldPassword')].messageId", hasItem(Message.EMPTY_FIELD.getId())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'newPassword')].errorMessage", hasItem(Message.SHORT_FIELD.getMessage())))
        .andExpect(jsonPath("$.errors[?(@.fieldName == 'newPassword')].messageId", hasItem(Message.SHORT_FIELD.getId())));
		
		 User user = userService.getUserByEmail("denismateescu@gmail.com");
		
		 assertTrue(passwordEncoder.matches("password", user.getPassword()));
	}
}

