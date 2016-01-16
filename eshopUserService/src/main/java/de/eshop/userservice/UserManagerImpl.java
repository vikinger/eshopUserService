package de.eshop.userservice;

import de.eshop.userservice.UserManager;
import de.eshop.userservice.RoleDAO;
import de.eshop.userservice.UserDAO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import de.eshop.userservice.Role;
import de.eshop.userservice.User;

/**
 * 
 * @author knad0001
 */

@Repository
public class UserManagerImpl implements UserManager {
	UserDAO helper;
	private final Map<String, User> userCache = new LinkedHashMap<String, User>();
	
	public UserManagerImpl() {
		helper = new UserDAO();
	}

	@Override
	public void registerUser(User user) {

		User newUser = new User(user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getRole());

		helper.saveObject(newUser);
	}
	
	@Override
	@HystrixCommand(fallbackMethod = "getUserFromCache", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public User getUserByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}
		User tempUser = helper.getUserByUsername(username);
		userCache.putIfAbsent(username, tempUser);
		return tempUser;
	}
	
	public User getUserFromCache(String username) {
		return userCache.getOrDefault(username, new User());
	}

	@Override
	public boolean deleteUserById(int id) {
		User user = new User();
		user.setId(id);
		helper.deleteObject(user);
		return true;
	}

	@Override
	public Role getRoleByLevel(int level) {
		RoleDAO roleHelper = new RoleDAO();
		return roleHelper.getRoleByLevel(level);
	}

	@Override
	public boolean doesUserAlreadyExist(String username) {
		
    	User dbUser = this.getUserByUsername(username);
    	
    	if (dbUser != null){
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	

	public boolean validate(User user) {
		if (user.getFirstname().isEmpty() || user.getPassword().isEmpty() || user.getRole() == null || user.getLastname() == null || user.getUsername() == null) {
			return false;
		}
		
		return true;
	}


	@Override
	public List<User> getAllUsers() {
		return helper.getObjectList();
	}

}
