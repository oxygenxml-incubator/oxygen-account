package com.oxygenxml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oxygenxml.model.User;

/**

The com.oxygenxml.repository package contains interfaces and classes related to the repository layer of the application.

The UserRepository interface extends the JpaRepository interface to provide database access methods for the User entity.

It handles CRUD operations for User objects. 

*/

public interface UserRepository extends JpaRepository<User, Integer> {

}
