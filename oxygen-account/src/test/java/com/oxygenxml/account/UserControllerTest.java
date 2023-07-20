package com.oxygenxml.account;

import com.oxygenxml.account.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.converter.UserConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserConverter userConverter;

	    @BeforeEach
	    public void setup() {
	    	
	        userService.deleteAll();
	    }

    @Test
    public void testRegisterUser() throws Exception {
    	
        UserDto newUser = new UserDto();
        
        newUser.setName("test");
        newUser.setEmail("teesting@test.com");
        newUser.setPassword("test");

        mockMvc.perform(post("/api/users/register")
            .contentType("application/json")
            .content(asJsonString(newUser)))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testRegisterSameEmail() throws Exception{
    	
    	UserDto existingUser = new UserDto();
    	
    	existingUser.setName("Text");
    	existingUser.setEmail("teest@gmail.com");
    	existingUser.setPassword("password123");
    	
    	userService.registerUser(userConverter.dtoToEntity(existingUser));
    	
    	UserDto newUser = new UserDto();
    	
    	newUser.setName("Test");
    	newUser.setEmail("teest@gmail.com");
    	newUser.setPassword("password");
    	
    	mockMvc.perform(post("/api/users/register")
    	          .contentType("application/json")
    	          .content(asJsonString(newUser)))
    	          .andExpect(status().isConflict());
    }

    /**
     * Helper method to convert an object to JSON string.
     */
    private String asJsonString(final Object obj) {
    	
        try {
        	
            return new ObjectMapper().writeValueAsString(obj);
            
        } catch (Exception e) {
        	
            throw new RuntimeException(e);
        }
    }
}
