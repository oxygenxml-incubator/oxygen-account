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
@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
class LoginTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @MockBean
    private OxygenUserDetailsService userDetailsService;
    
    @BeforeEach
    void buildUser() {
        UserDetails mockUser = User.builder()
            .username("test@email.com")
            .password(passwordEncoder.encode("testPassword"))
            .build();

        when(userDetailsService.loadUserByUsername("test@email.com"))
            .thenReturn(mockUser);
    }
    
    @Test
    void testSuccessfulAuthentication() throws Exception {
    	
        mockMvc.perform(post("/login")
        		.contentType(APPLICATION_FORM_URLENCODED)
                .param("email", "test@email.com")
                .param("password", "testPassword"))
               .andExpect(status().isFound());
    }
    
    @Test
    void testAccesProfilePath() throws Exception{
    	
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
    
    @Test
    void testUnsuccessfulAuthentication() throws Exception {
    	
        mockMvc.perform(post("/login")
        		.contentType(APPLICATION_FORM_URLENCODED)
                .param("email", "test@email.com")
                .param("password", "test"))
               .andExpect(status().isFound())
               .andExpect(redirectedUrl("/login#invalid-user"));
    }
    
    @Test
    void testCantAccesProfile() throws Exception{
    	
    	 mockMvc.perform(get("/profile"))
         .andExpect(status().isFound()) 
         .andExpect(redirectedUrlPattern("**/login"));
    }
}
