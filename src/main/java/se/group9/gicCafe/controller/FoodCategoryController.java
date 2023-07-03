package se.group9.gicCafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import se.group9.gicCafe.model.FoodCategory;
import se.group9.gicCafe.service.FoodCategoryService;

@RequestMapping("/admin")
@Controller
public class FoodCategoryController {
    private FoodCategoryService foodCategoryService;

    public FoodCategoryController(FoodCategoryService foodCategoryService) {
        super();
        this.foodCategoryService = foodCategoryService;
    }

    @GetMapping("/foodCategory/new")
	public String createFoodCategoryForm(Model model) {
		FoodCategory foodCategory = new FoodCategory();
		model.addAttribute("foodCategory", foodCategory);
		
		return "admin/createFoodCategory";
	}
	
	@PostMapping("/foodCategory")
	public String saveFoodCategory(@ModelAttribute("foodCategory") FoodCategory foodCategory) {	
		foodCategoryService.saveFoodCategory(foodCategory);
        
		return "redirect:/admin/foods";
	}
}
