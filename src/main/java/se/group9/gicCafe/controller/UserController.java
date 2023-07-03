package se.group9.gicCafe.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import se.group9.gicCafe.service.UserService;
import se.group9.gicCafe.constants.CONSTANT;
import se.group9.gicCafe.model.User;

@RequestMapping("/admin")
@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/cashiers")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

		return "admin/cashiers/viewCashier";
    }

    @GetMapping("/cashiers/detail/{id}")
	public String cashierDetail(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "admin/cashiers/cashierDetail";
	}


	@GetMapping("/cashiers/new")
	public String createCashierForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		return "admin/cashiers/createCashier";
	}
	
	@PostMapping("/cashiers")
	public String saveCashier(@RequestParam("file") MultipartFile file,@Validated @ModelAttribute("user") User user) {
		LocalDateTime today = LocalDateTime.now();
		user.setLast_login(today);
		user.setRole(CONSTANT.Cashier_Role);
		//input an image
		String img = StringUtils.cleanPath(file.getOriginalFilename());
		if(img.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			// user.setImage(file.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			userService.saveUser(user);
		} catch(Exception e) {
			return "admin/cashiers/errorCashier";
		}
		return "redirect:/admin/cashiers";
	}

    @GetMapping("/cashiers/edit/{id}")
	public String editCashierForm(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "admin/cashiers/editCashier";
	}

	@PostMapping("/cashiers/{id}")
	public String updateCashier(@PathVariable int id,
			@Validated @ModelAttribute("user") User user,
			@RequestParam("file") MultipartFile file,
			Model model) {
		
		// get student from database by id
		User existingUser = userService.getUserById(id);
		existingUser.setId(id);
		existingUser.setFirstname(user.getFirstname());
		existingUser.setLastname(user.getLastname());
		existingUser.setGender(user.getGender());
        existingUser.setDate_of_birth(user.getDate_of_birth());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());

		String image = StringUtils.cleanPath(file.getOriginalFilename());
		if(image.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			existingUser.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save updated User object
		try {
			userService.updateUser(existingUser);
		} catch(Exception e) {
			return "admin/cashiers/errorCashier";
		}
		
		return "redirect:/admin/cashiers";		
	}
	
	// handler method to handle delete student request
	
	@GetMapping("/cashiers/{id}")
	public String deleteCashier(@PathVariable int id) {
		userService.deleteUserById(id);
		return "redirect:/admin/cashiers";
	}
}


//note
//add image
// make table in drink and food view detail
// order history
// after that test and again and finish
