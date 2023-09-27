package com.oxygenxml.account.email;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.events.RegistrationEvent;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.model.UserStatus;
import com.oxygenxml.account.service.EventService;
import com.oxygenxml.account.service.JwtService;
import com.oxygenxml.account.service.UserService;
import com.oxygenxml.account.utility.JsonUtil;

/**
 * ClasaEmail tests the functioning of the email sending system
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
public class EmailTest {

	/**
	 * Holds the token extracted during the user registration event.
	 */
	private String extractedToken;
	
	/**
	 * It's used to simulate the handling of user registration events for testing purposes.
	 */
    @MockBean
    private EventService eventService;
    
    /**
     * Instance for handling JWT operations such as token generation and parsing.
     */
    @Autowired
    private JwtService jwtService;
    
    /**
	 * MockMvc instance is used for simulating HTPP requests
	 */
	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * The instance used for user-related operations
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * It is used to publish application events such as registration events for testing purposes.
	 */
	@MockBean
    private ApplicationEventPublisher eventPublisher;
    
	/**
	 * Listens to user registration events and handles token generation for email confirmation.
	 */
    @EventListener
    public void handleUserRegistrationTest(RegistrationEvent event) {
        User registeredUser = event.getUser();
        
        extractedToken = jwtService.generateEmailConfirmationToken(registeredUser.getId(), registeredUser.getRegistrationDate());
    }
    
    /**
     * Tests the whole flow of a new user registration and the email confirmation
     */
    @Test
	void confirmNewUser() throws Exception {
		UserDto newUser = new UserDto();

		newUser.setName("Denis");
		newUser.setEmail("denis@gmail.com");
		newUser.setPassword("password");
		
		Mockito.doAnswer(invocation -> {
            RegistrationEvent event = invocation.getArgument(0);
            handleUserRegistrationTest(event);
            return null;
        }).when(eventService).handleUserRegistration(any(RegistrationEvent.class));
		
		ResultActions resultAction = mockMvc.perform(post("/api/users/register")
				.contentType("application/json")
				.content(JsonUtil.asJsonString(newUser)));
		resultAction.andExpect(status().isOk());
		
		User user = userService.getUserByEmail("denis@gmail.com");
		
		assertEquals(UserStatus.NEW.getStatus(), user.getStatus());
		assertNotNull(user.getId());
		
		mockMvc.perform(get("/api/users/confirm")
                .param("token", extractedToken))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login#success-confirmation"));
		
		user = userService.getUserByEmail("denis@gmail.com");
        assertEquals(UserStatus.ACTIVE.getStatus(), user.getStatus());
	}
}
