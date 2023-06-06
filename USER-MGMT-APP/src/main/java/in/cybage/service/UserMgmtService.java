package in.cybage.service;

import java.util.List;

import in.cybage.bindings.ActivateAccount;
import in.cybage.bindings.Login;
import in.cybage.bindings.User;

public interface UserMgmtService {
 public boolean saveuser( User user);
 
 public boolean activateuserAcc( ActivateAccount activateAcc);
 
 public List<User> getAllUsers();
 
 public User getUserById(Integer userId);
 
 public boolean deleteUserById(Integer userId);
 
 public boolean changeStatus(Integer userId, String accStatus);
 
 public String login(Login login);
 
 public String forgotPwd(String email);
 
 
 
 
}
