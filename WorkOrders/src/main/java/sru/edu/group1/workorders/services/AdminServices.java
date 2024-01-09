package sru.edu.group1.workorders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;
import sru.edu.group1.workorders.domain.*;
import sru.edu.group1.workorders.repository.*;

/**
 * list of services an Admin account will have over user accounts, mostly just buttons on the admin list of users, but important none the less
 * 
 * @author Trevor Bell
 * @author Ellie Wurst
 * @author John Friend
 * @author Hunter Minteer
 */
@Service
public class AdminServices {

	@Autowired
	private UserRepository userRepo;

	
	/**
	 * Will disable a user account, while also setting their verificaiton code again in case they wish to re-verify themselves
	 * @param user holds the information of the user the admin wishes to disable 
	 */
	public void disableUserAccount(Users user)
	{
		String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
		user.setEnabled(false);
		userRepo.save(user);
	}
	/**
	 * Enables user account, used by admins for when a user may make a request or if an account didnt' get enabled properly
	 * @param user holds current user data
	 */
	public void enableUserAccount(Users user)
	{
		user.setEnabled(true);
		userRepo.save(user);
	}
	
	/**
	 * fully deletes the user account, useful for removing accounts which may not see use or outright fake accounts
	 * @param user holds user data
	 */
	public void deleteUserAccount(Users user)
	{
		userRepo.delete(user);
	}
}
