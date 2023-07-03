package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.User;

public interface UserService {
    List<User> getAllUsers();
	
	User saveUser(User User);
	
	User getUserById(int id);
	
	User updateUser(User User);
	
	void deleteUserById(int id);


	User getUserByUsername(String username);
}
