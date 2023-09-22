package com.oxygenxml.account.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oxygenxml.account.model.User;
import com.oxygenxml.account.model.UserStatus;
import com.oxygenxml.account.repository.UserRepository;
import com.oxygenxml.account.utility.DateUtility;

/**
 * A scheduled task that processes deleted users on a daily basis, checking if the deleted users should be permanently deleted from the database.
 */
@Component
public class DeletedUsersTask {
	
	/**
	 * Instance of UserRepository to interact with the database.
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Instance of a utility class to calculate the number of days left before a deleted user should be permanently deleted.
	 */
	@Autowired
	private DateUtility daysLeftUtility;

	/**
	 * Scheduled task that runs daily at midnight to process users marked as "deleted" and check if they should be permanently deleted from the repository.
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void processDeletedUsers() {
		List<User> deletedUsers = userRepository.findByStatus(UserStatus.DELETED.getStatus());

		for (User user : deletedUsers) {
			int daysLeft = daysLeftUtility.getDaysLeftForRecovery(user);

			if (daysLeft == 0) {
				userRepository.delete(user);
			}
		}
	}
}
