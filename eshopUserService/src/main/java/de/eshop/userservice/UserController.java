package de.eshop.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.eshop.userservice.UserManagerImpl;

@RestController
public class UserController {
	
	@Autowired
	private UserManager repo;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		repo.registerUser(user);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByUsername(@PathVariable String username){
		User user = repo.getUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUserById(@PathVariable Integer userId) {
		repo.deleteUserById(userId);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getAllUsers(){
		Iterable<User> allUsers = repo.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/role/{roleLevel}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRoleByLevel(@PathVariable Integer roleLevel){
		Role role = repo.getRoleByLevel(roleLevel);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<Boolean> doesUserAlreadyExist (@RequestParam("username") String username){
		boolean userExist = repo.doesUserAlreadyExist(username);
		return new ResponseEntity<>(userExist, HttpStatus.OK);
	}

//	@RequestMapping(value = "/users", method = RequestMethod.GET)
//	public ResponseEntity<Iterable<User>> getUsers() {
//		Iterable<User> allPolls = repo.findAll();
//		return new ResponseEntity<>(allPolls, HttpStatus.OK);
//	}
	
//	@RequestMapping(value = "/user", method = RequestMethod.GET)
//	public ResponseEntity<User> getUserByUsername (@RequestParam("username") String username){
//		User user = repo.getUserByUsername(username);
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}
	
//	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
//	public ResponseEntity<Boolean> doesUserAlreadyExist (@PathVariable String username, @RequestParam("exist") String exist){
//		boolean userExist = repo.doesUserAlreadyExist(username);
//		return new ResponseEntity<>(userExist, HttpStatus.OK);
//	}

//	@RequestMapping(value = "/users", method = RequestMethod.POST)
//	public ResponseEntity<?> addUser(@RequestBody User user) {
//		user = repo.save(user);
//		return new ResponseEntity<>(null, HttpStatus.CREATED);
//	}
//
//	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
//	public ResponseEntity<User> getUser(@PathVariable Long userId) {
//		User user = repo.findOne(userId);
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}
}