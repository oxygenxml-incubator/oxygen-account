package com.oxygenxml.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oxygenxml.account.model.User;

/**

The com.oxygenxml.repository package contains interfaces and classes related to the repository layer of the application.

The UserRepository interface extends the JpaRepository interface to provide database access methods for the User entity.

It handles CRUD operations for User objects. 

*/

public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * Checks if a User entity with the provided email exists in the database.
	 *
	 * @param email The email to check for in the database.
	 * @return A boolean value representing whether a User with the provided email exists.
	 */
	boolean existsByEmail(String email);
	/**
	 * Checks if a User entity with the provided username exists in the database.
	 *
	 * @param username The username to check for in the database.
	 * @return A boolean value representing whether a User with the provided username exists.
	 */
	boolean existsByname(String name);
}
