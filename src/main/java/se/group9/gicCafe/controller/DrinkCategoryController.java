package se.group9.gicCafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import se.group9.gicCafe.model.DrinkCategory;
import se.group9.gicCafe.service.DrinkCategoryService;

@RequestMapping("/admin")
@Controller
public class DrinkCategoryController {

    private DrinkCategoryService drinkCategoryService;

    public DrinkCategoryController(DrinkCategoryService drinkCategoryService) {
        super();
        this.drinkCategoryService = drinkCategoryService;
    }

    @GetMapping("/drinkCategory/new")
	public String createDrinkCategoryForm(Model model) {
		DrinkCategory drinkCategory = new DrinkCategory();
		model.addAttribute("drinkCategory", drinkCategory);
		
		return "admin/createDrinkCategory";
	}
	
	@PostMapping("/drinkCategory")
	public String saveDrinkCategory(@ModelAttribute("drinkCategory") DrinkCategory drinkCategory) {	
		drinkCategoryService.saveDrinkCategory(drinkCategory);
        
		return "redirect:/admin/drinks";
	}
}
