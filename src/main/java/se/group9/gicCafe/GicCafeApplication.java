package se.group9.gicCafe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import se.group9.gicCafe.constants.CONSTANT;
import se.group9.gicCafe.model.User;
import se.group9.gicCafe.service.UserService;

@SpringBootApplication
public class GicCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GicCafeApplication.class, args);
	}

	@Component
	public class DataInitializer implements CommandLineRunner {
		@Autowired
		private UserService userService;

		@Override
		public void run(String... args) throws Exception {
			if (userService.getUserByUsername(CONSTANT.Admin_Role) == null) {
				User admin = new User(0, "Admin", "Admin", "Female",
						null, "Admin", "123", null, 
						CONSTANT.Admin_Role, null, null);
				userService.saveUser(admin);
			}
		}
	}
}
