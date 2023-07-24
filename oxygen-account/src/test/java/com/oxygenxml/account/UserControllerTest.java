package com.oxygenxml.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxygenxml.account.dto.UserDto;
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
import static org.hamcrest.Matchers.is;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The UserControllerTest class tests the functionality of UserController
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@TestPropertySource(locations="classpath:application-test.properties")
@SqlGroup({
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/migration/V1_0__create_table.sql"),
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/PopulateDatabase.sql"),
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
    
    /**
     * The testRegisterSameEmail method tests whether another user with the same email can be registered
     * It registers an initial user, then attempts to register a new user with the same email.
     * @throws Exception if the test encounters any errors
     */
    @Test
    public void testRegisterSameEmail() throws Exception{
    	
    	UserDto newUser = new UserDto();
    	
    	newUser.setName("Test");
    	newUser.setEmail("test@gmail.com");
    	newUser.setPassword("password");
    	
    	mockMvc.perform(post("/api/users/register")
    	          .contentType("application/json")
    	          .content(asJsonString(newUser)))
    	          .andExpect(status().isConflict())
    	          .andExpect(jsonPath("$.errorMessage", is("User with this email already exists.")));
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
