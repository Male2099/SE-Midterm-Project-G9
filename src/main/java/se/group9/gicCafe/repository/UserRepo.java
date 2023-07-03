package se.group9.gicCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.group9.gicCafe.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
