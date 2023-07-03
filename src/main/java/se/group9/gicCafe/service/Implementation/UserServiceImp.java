package se.group9.gicCafe.service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import se.group9.gicCafe.model.User;
import se.group9.gicCafe.repository.UserRepo;
import se.group9.gicCafe.service.UserService;

@Service
public class UserServiceImp implements UserService{

    private UserRepo userRepo;

    public UserServiceImp(UserRepo userRepo) {
        super();
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUserById(int id) {
		return userRepo.findById(id).get();
    }

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public void deleteUserById(int id) {
		userRepo.deleteById(id);	
	}

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    
}
