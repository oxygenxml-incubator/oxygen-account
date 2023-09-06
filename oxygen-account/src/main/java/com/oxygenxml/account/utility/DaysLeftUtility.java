package com.oxygenxml.account.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oxygenxml.account.Config.AppConfig;
import com.oxygenxml.account.model.User;

@Component
public class DaysLeftUtility {
	
	@Autowired
	private AppConfig appConfig;
	
	private final static long MILIS_IN_DAY = 24L * 60L * 60L * 1000L;
	
	public int getDaysLeftForRecovery(User user) {
		long timeSinceDeletion = System.currentTimeMillis() - user.getDeletionDate().getTime();
		int daysSinceDeletion = (int) (timeSinceDeletion / MILIS_IN_DAY);
		int daysLeft = appConfig.getDaysUntilDeletion() - daysSinceDeletion;

		return Math.max(daysLeft, 0); 
	}
}
