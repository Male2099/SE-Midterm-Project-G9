package se.group9.gicCafe.security.login_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import se.group9.gicCafe.model.User;
import se.group9.gicCafe.service.UserService;

public class CustomUserDetailsService implements UserDetailsService {
  
  @Autowired private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User emp = userService.getUserByUsername(username);
    

    if (emp == null) {
      throw new UsernameNotFoundException("No user found for the given username");
    }
    return new UserDetailsImp(emp);
  }
}
