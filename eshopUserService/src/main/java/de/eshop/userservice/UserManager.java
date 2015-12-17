package de.eshop.userservice;

import java.util.List;
import de.eshop.userservice.Role;
import de.eshop.userservice.User;

public interface UserManager {
    
    public void registerUser(String username, String name, String lastname, String password, Role role);
    
    public User getUserByUsername(String username);
    
    public boolean deleteUserById(int id);
    
    public Role getRoleByLevel(int level);
    
    public boolean doesUserAlreadyExist(String username);
    
    public List<User> getAllUsers();
}
