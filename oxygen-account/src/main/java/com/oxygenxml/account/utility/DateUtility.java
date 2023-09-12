package com.oxygenxml.account.utility;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oxygenxml.account.Config.OxygenAccountPorpertiesConfig;
import com.oxygenxml.account.model.User;

/**
 * Utility class to handle date-related operations.
 */
@Component
public class DateUtility {
	
	/**
	 * Autowired configuration object to access application properties related to Oxygen account settings.
	 */
	@Autowired
	private OxygenAccountPorpertiesConfig oxygenProperties;
	
	/**
	 *  The number of milliseconds in a day, calculated as 24 hours x 60 minutes x 60 seconds x 1000 milliseconds.
	 */
	private final static long MILIS_IN_DAY = 24L * 60L * 60L * 1000L;
	
	/**
	 * Calculates the number of days left for a user's recovery based on the user's deletion date and the configured number of days until deletion.
	 * @param user The user whose recovery days left needs to be calculated
	 * @return the number of days left for recovery; returns zero if the user has no days left
	 */
	public int getDaysLeftForRecovery(User user) {
		long timeSinceDeletion = System.currentTimeMillis() - user.getDeletionDate().getTime();
		int daysSinceDeletion = (int) (timeSinceDeletion / MILIS_IN_DAY);
		int daysLeft = oxygenProperties.getDaysUntilDeletion() - daysSinceDeletion;

		return Math.max(daysLeft, 0); 
	}
	
	/**
	 * Retrieves the current UTC timestamp.
	 */
	public static Timestamp getCurrentUTCTimestamp() {
		return Timestamp.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
	}
}
