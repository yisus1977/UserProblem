package com.inter.persistance;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inter.model.User;

/**
 * @author Jesus
 *User Dao Interface
 */
@Repository
public interface UserDAO {
	
	
	/**
	 * @param username
	 * @return User from DB with username matching parameter
	 * @throws Exception
	 */
	User findByUserName(String username) throws Exception;

	/**
	 * @return A list of all users from DB
	 * @throws Exception
	 */
	List<User> findAll() throws Exception;

	/**
	 * Inserts Mock Data for testing purposes
	 */
	void insertMockData();

	/**
	 * @param username
	 * @return A list of all usernames that begin with the given parameter
	 * @throws Exception
	 */
	List<String> findAlikeUserName(String username) throws Exception;
	

}
