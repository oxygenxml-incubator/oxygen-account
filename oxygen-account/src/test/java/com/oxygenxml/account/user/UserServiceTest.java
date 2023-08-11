package com.oxygenxml.account.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.oxygenxml.account.dto.UserDto;
import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;
import com.oxygenxml.account.utility.JsonUtil;

/**
 * The UserServiceTest class tests the functionality of UserService
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/PopulateDatabase.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/ClearDatabase.sql")
})
public class UserServiceTest {

	/**
     * The password encoder that will be used to encode passwords
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * The UserRepository that will be used to manipulate the users in the database
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * The MockMvc instance that will be used to simulate HTTP requests
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test the server's ability to encode a password
     * @throws Exception
     */
    @Test
    void testPasswordEncodingByServer() throws Exception {

        UserDto newUser = new UserDto();
        newUser.setName("test");
        newUser.setEmail("test@test.com");
        newUser.setPassword("password");

        ResultActions resultAction = mockMvc.perform(post("/api/users/register")
                .contentType("application/json")
                .content(JsonUtil.asJsonString(newUser)));
        resultAction.andExpect(status().isOk());

        User registeredUser = userRepository.findByEmail("test@test.com");
        assertTrue(passwordEncoder.matches("password", registeredUser.getPassword()));
    }

    /**
     * Test that the password encoding is consistent
     */
    @Test
    void testPasswordEncodingConsistency() {
        String rawPassword = "password";
        
        String encodedPassword1 = "$2a$10$yWjIRyR/PQu2nS/0jzQa6.lj0YxI/Hc56fb/MD8rLteQe7kYn.NLS";

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword1));
    }    
}
