package com.oxygenxml.account.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oxygenxml.account.model.User;
import com.oxygenxml.account.repository.UserRepository;
import com.oxygenxml.account.utility.DateUtility;
import com.oxygenxml.account.utility.UserStatus;

@Component
public class DeletedUsersTask {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DateUtility daysLeftUtility;

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
