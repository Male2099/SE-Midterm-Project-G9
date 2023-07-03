package se.group9.gicCafe.security.login_security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import se.group9.gicCafe.model.User;

public class UserDetailsImp implements UserDetails {

  private User user;
  public UserDetailsImp(User user){
    this.user=user;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole()));
    return authorities;
  }


  public boolean hasRole(String roleName) {
    return this.user.getRole().equals(roleName);
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
