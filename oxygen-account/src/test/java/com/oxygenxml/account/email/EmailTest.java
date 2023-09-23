package com.oxygenxml.account.email;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    private EventService eventService;
    
    @Autowired
    private JwtService jwtService;
    
    private String extractedToken;
    
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@MockBean
    private ApplicationEventPublisher eventPublisher;
    
    @EventListener
    public void handleUserRegistration(RegistrationEvent event) {
        User registeredUser = event.getUser();
        
        extractedToken = jwtService.generateEmailConfirmationToken(registeredUser.getId(), registeredUser.getRegistrationDate());
    }
    
    @Test
	void confirmNewUser() throws Exception {
		UserDto newUser = new UserDto();

		newUser.setName("Denis");
		newUser.setEmail("denis@gmail.com");
		newUser.setPassword("password");
		
		Mockito.doAnswer(invocation -> {
            RegistrationEvent event = invocation.getArgument(0);
            handleUserRegistration(event);
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
                .andExpect(status().is3xxRedirection());
		
		user = userService.getUserByEmail("denis@gmail.com");
        assertEquals(UserStatus.ACTIVE.getStatus(), user.getStatus());
	}

    
}
