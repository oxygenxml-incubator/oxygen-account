package com.oxygenxml.account.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oxygenxml.account.OxygenAccountApplication;
import com.oxygenxml.account.service.OxygenUserDetailsService;

/**
 * Unit tests for the Login Controller.
 */
@SpringBootTest(classes=OxygenAccountApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
class LoginControllerTest {
	
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
    public void testSuccessfulAuthentication() throws Exception {
    	
        mockMvc.perform(post("/login")
        		.contentType(APPLICATION_FORM_URLENCODED)
                .param("email", "test@email.com")
                .param("password", "testPassword"))
               .andExpect(status().isFound())
               .andExpect(redirectedUrl("/"));
    }
    
    @Test
    public void testUnsuccessfulAuthentication() throws Exception {
    	
        mockMvc.perform(post("/login")
        		.contentType(APPLICATION_FORM_URLENCODED)
                .param("email", "test@email.com")
                .param("password", "test"))
               .andExpect(status().isFound())
               .andExpect(redirectedUrl("/login?error"));
    }
}
