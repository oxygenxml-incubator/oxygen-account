package com.oxygenxml.account.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.oxygenxml.account.OxygenAccountApplication;



/**
 * Unit tests for the Login Controller.
 */
@SpringBootTest(classes=OxygenAccountApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    /**
     * Tests the functionality of the showLoginForm() method from the Login Controller.
     */
    @Test
    public void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
