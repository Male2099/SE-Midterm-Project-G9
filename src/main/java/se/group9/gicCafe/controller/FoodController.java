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

import se.group9.gicCafe.model.Food;
import se.group9.gicCafe.service.FoodCategoryService;
import se.group9.gicCafe.service.FoodService;

@RequestMapping("/admin")
@Controller
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodCategoryService foodCategoryService;

    public FoodController(FoodService foodService) {
        super();
        this.foodService = foodService;
    }

    @GetMapping("/foods")
    public String listFoods(Model model) {
        model.addAttribute("foods", foodService.getAllFoods());

        return "admin/foods/viewFood";
    }

    @GetMapping("/foods/detail/{id}")
    public String foodDetail(@PathVariable int id, Model model) {
        model.addAttribute("food", foodService.getFoodById(id));

        model.addAttribute("foodCategories", foodCategoryService.getAllFoodCategories());
        return "admin/foods/foodDetail";
    }

    @GetMapping("/foods/new")
    public String createFoodForm(Model model) {
        Food food = new Food();
        model.addAttribute("food", food);

        model.addAttribute("foodCategories", foodCategoryService.getAllFoodCategories());

        return "admin/foods/createFood";
    }

    @PostMapping("/foods")
    public String saveFood(@Validated @ModelAttribute("food") Food food, @RequestParam("file") MultipartFile file) {
        // input an image
        String img = StringUtils.cleanPath(file.getOriginalFilename());
        if (img.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            food.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            // user.setImage(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            foodService.saveFood(food);
        } catch (Exception e) {
            return "admin/foods/errorFood";
        }

        return "redirect:/admin/foods";
    }

    @GetMapping("/foods/edit/{id}")
    public String editFoodForm(@PathVariable int id, Model model) {
        model.addAttribute("food", foodService.getFoodById(id));

        model.addAttribute("foodCategories", foodCategoryService.getAllFoodCategories());

        return "admin/foods/editFood";
    }

    @PostMapping("/foods/{id}")
    public String updateFood(@PathVariable int id,
            @Validated @ModelAttribute("food") Food food,
            @RequestParam("file") MultipartFile file,
            Model model) {

        model.addAttribute("foodCategories", foodCategoryService.getAllFoodCategories());

        // get student from database by id
        Food existingFood = foodService.getFoodById(id);
        existingFood.setId(id);
        existingFood.setCode(food.getCode());
        existingFood.setName(food.getName());
        existingFood.setFoodCategory(food.getFoodCategory());
        existingFood.setPrice(food.getPrice());
        existingFood.setNote(food.getNote());

        String img = StringUtils.cleanPath(file.getOriginalFilename());
        if (img.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            existingFood.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            // user.setImage(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // save updated Food object
        try {
            foodService.updateFood(existingFood);
        } catch (Exception e) {
            return "admin/foods/errorFood";
        }

        return "redirect:/admin/foods";
    }

    // handler method to handle delete student request

    @GetMapping("/foods/{id}")
    public String deleteFood(@PathVariable int id) {
        foodService.deleteFoodById(id);
        return "redirect:/admin/foods";
    }
}
