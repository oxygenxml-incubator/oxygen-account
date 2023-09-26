package com.oxygenxml.account.login;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.service.OxygenUserDetailsService;

/**
 * Unit tests for the Login Controller.
 */
@SpringBootTest(classes=OxygenAccountApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(locations="classpath:application-test.properties")
class LoginTest {
	
	/**
	 * The MockMvc instance is used for simulating HTPP requests
	 */
    @Autowired
    private MockMvc mockMvc;
    
    /**
     * Encoder for encoding and matching passwords.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Mocked user details service for user-related operations.
     */
    @MockBean
    private OxygenUserDetailsService userDetailsService;
    
    /**
     * Setting up a mock user before each test.
     */
    @BeforeEach
    void buildUser() {
        UserDetails mockUser = User.builder()
            .username("test@email.com")
            .password(passwordEncoder.encode("testPassword"))
            .build();

        when(userDetailsService.loadUserByUsername("test@email.com"))
            .thenReturn(mockUser);
    }
    
    /**
     * Tests if the authentication is successful.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testSuccessfulAuthentication() throws Exception {
    	
        mockMvc.perform(post("/login")
        		.contentType(APPLICATION_FORM_URLENCODED)
                .param("email", "test@email.com")
                .param("password", "testPassword"))
               .andExpect(status().isFound());
    }
    
    /**
     * Tests accessing the profile path before logging in and after log in.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testAccessProfilePathBeforeLogin() throws Exception{
    	
    	ResultActions resultAction = mockMvc.perform(get("/profile"))
        .andExpect(status().isFound()) 
        .andExpect(redirectedUrlPattern("**/login"));
    	
    	MvcResult result = resultAction.andReturn();
    	MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
    	
    	  mockMvc.perform(post("/login").session(session)
    			.contentType(APPLICATION_FORM_URLENCODED)
    			.param("email", "test@email.com")
    			.param("password", "testPassword"))
    			.andExpect(status().isFound())
    			.andExpect(redirectedUrlPattern("**/profile?continue"));
    }
    
    /**
     * Tests accessing the profile path in another window after a user log in.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test

    void testAccessProfilePathInAnotherWindows() throws Exception {

         ResultActions resultActions = mockMvc.perform(post("/login")
                 .contentType(APPLICATION_FORM_URLENCODED)
                 .param("email", "test@email.com")
                 .param("password", "testPassword"))
                 .andExpect(status().isFound())
                 .andExpect(redirectedUrl("/"));

          MvcResult result = resultActions.andReturn();
          MockHttpSession session = (MockHttpSession) result.getRequest().getSession();

          mockMvc.perform(get("/profile").session(session))
          .andExpect(status().isOk())
          .andExpect(view().name("profile"));
    }
    
    /**
     * Tests if authentication fails when a user with the wrong credentials is entered.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testUnsuccessfulAuthentication() throws Exception {
    	
        mockMvc.perform(post("/login")
        		.contentType(APPLICATION_FORM_URLENCODED)
                .param("email", "test@email.com")
                .param("password", "test"))
               .andExpect(status().isFound())
               .andExpect(redirectedUrl("/login#invalid-user"));
    }
    
    /**
     * Tests if the user can't access the profile without authentication.
     * 
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCantAccessProfile() throws Exception{
    	
    	 mockMvc.perform(get("/profile"))
         .andExpect(status().isFound()) 
         .andExpect(redirectedUrlPattern("**/login"));
    }    
}
