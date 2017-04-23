package com.inter.service;

import java.util.HashMap;
import java.util.List;

import com.inter.model.User;

/**
 * @author Jesus
 *User Service Interface
 */
public interface UserService {
	
	/**
	 * @param username
	 * @return Map with key true if is a valid username, key false with a list of available usernames in values
	 * @throws Exception
	 */
	HashMap<Boolean, List<String>> checkUsername(String username) throws Exception;

	/**
	 * @return a list of all users on DB
	 * @throws Exception
	 */
	List<User> getAllUsers() throws Exception;

	/**
	 * @param username
	 * @return true if there is a user in DB with the given username
	 * @throws Exception
	 */
	boolean isUser(String username) throws Exception;

	/**
	 * This inserts mock data in DB for testing purposes
	 * @throws Exception
	 */
	void insertMockData() throws Exception;

}
