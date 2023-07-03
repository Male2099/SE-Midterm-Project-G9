package se.group9.gicCafe.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
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

import se.group9.gicCafe.model.Drink;
import se.group9.gicCafe.service.DrinkCategoryService;
import se.group9.gicCafe.service.DrinkService;

@RequestMapping("/admin")
@Controller
public class DrinkController {
    @Autowired
    private DrinkService drinkService;

    @Autowired
    private DrinkCategoryService drinkCategoryService;

    public DrinkController(DrinkService drinkService) {
        super();
        this.drinkService = drinkService;
    }

    @GetMapping("/drinks")
    public String listDrinks(Model model) {
        model.addAttribute("drinks", drinkService.getAllDrinks());

        return "admin/drinks/viewDrink";
    }

    @GetMapping("/drinks/detail/{id}")
    public String drinkDetail(@PathVariable int id, Model model) {
        model.addAttribute("drink", drinkService.getDrinkById(id));

        model.addAttribute("drinkCategories", drinkCategoryService.getAllDrinkCategories());
        return "admin/drinks/drinkDetail";
    }
    

    @GetMapping("/drinks/new")
    public String createDrinkForm(Model model) {
        Drink drink = new Drink();
        model.addAttribute("drink", drink);

        model.addAttribute("drinkCategories", drinkCategoryService.getAllDrinkCategories());

        return "admin/drinks/createDrink";
    }

    @PostMapping("/drinks")
    public String saveDrink(@Validated @ModelAttribute("drink") Drink drink, @RequestParam("file") MultipartFile file) {
        // input an image
        String img = StringUtils.cleanPath(file.getOriginalFilename());
        if (img.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            drink.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            // user.setImage(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            drinkService.saveDrink(drink);
        } catch (Exception e) {
            return "admin/drinks/errorDrink";
        }

        return "redirect:/admin/drinks";
    }

    @GetMapping("/drinks/edit/{id}")
    public String editDrinkForm(@PathVariable int id, Model model) {
        model.addAttribute("drink", drinkService.getDrinkById(id));

        model.addAttribute("drinkCategories", drinkCategoryService.getAllDrinkCategories());

        return "admin/drinks/editDrink";
    }

    @PostMapping("/drinks/{id}")
    public String updateDrink(@PathVariable int id,
            @Validated @ModelAttribute("drink") Drink drink,
            @RequestParam("file") MultipartFile file,
            Model model) {

        model.addAttribute("drinkCategories", drinkCategoryService.getAllDrinkCategories());

        // get student from database by id
        Drink existingDrink = drinkService.getDrinkById(id);
        existingDrink.setId(id);
        existingDrink.setCode(drink.getCode());
        existingDrink.setName(drink.getName());
        existingDrink.setDrinkCategory(drink.getDrinkCategory());
        existingDrink.setSize_R(drink.getSize_R());
        existingDrink.setSize_L(drink.getSize_L());
        existingDrink.setSize_G(drink.getSize_G());
        existingDrink.setNote(drink.getNote());
        existingDrink.setImage("null");

        String img = StringUtils.cleanPath(file.getOriginalFilename());
        if (img.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            existingDrink.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            // user.setImage(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // save updated Drink object
        try {
            drinkService.updateDrink(existingDrink);
        } catch (Exception e) {
            return "admin/drinks/errorDrink";
        }

        return "redirect:/admin/drinks";
    }

    // handler method to handle delete student request

    @GetMapping("/drinks/{id}")
    public String deleteDrink(@PathVariable int id) {
        drinkService.deleteDrinkById(id);
        return "redirect:/admin/drinks";
    }
}
