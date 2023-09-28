package com.oxygenxml.account.task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import com.oxygenxml.account.OxygenAccountApplication;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;
import com.oxygenxml.account.utility.DateUtility;

/**
 * Class that tests the tasks 
 *
 */
@SpringBootTest(classes=OxygenAccountApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(locations="classpath:application-test.properties")
class TaskTest {

	/**
	 * Instance for interaction with database
	 */
    @Mock
    private UserRepository userRepository;

    /**
     * Instance to access the methods from DateUtility
     */
    @Mock
    private DateUtility dateUtility;

    /**
     * Instance for access the tasks from DeletedUsersTask
     */
    @InjectMocks
    private DeletedUsersTask deletedUsersTask;

    /**
     * Tests the task for deleted users
     */
    @Test
    void testProcessRemoveDeletedUsers() {
        User user = mock(User.class);
        
        when(userRepository.findByStatus(anyString())).thenReturn(Collections.singletonList(user));

        when(dateUtility.getDaysLeftForRecovery(any())).thenReturn(0);

        deletedUsersTask.processDeletedUsers();

        verify(userRepository, times(1)).delete(user);
    }
    
    /**
     * Test the task for unconfirmed users
     */
    @Test
    void testProcessRemoveUnconfirmedUsers() {
        User user = mock(User.class);
        
        when(userRepository.findByStatus(anyString())).thenReturn(Collections.singletonList(user));

        when(dateUtility.getDaysLeftForConfirmAccount(any())).thenReturn(0);

        deletedUsersTask.processDeleteUnconfirmedUsers();

        verify(userRepository, times(1)).delete(user);
    }
}

