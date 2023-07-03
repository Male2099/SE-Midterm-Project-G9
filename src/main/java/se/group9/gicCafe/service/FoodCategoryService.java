package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.FoodCategory;

public interface FoodCategoryService {
    List<FoodCategory> getAllFoodCategories();

    FoodCategory getFoodCategoryByID(int id);
    

    FoodCategory saveFoodCategory(FoodCategory foodCategory);
	
    // FoodCategory getFoodCategoryById(int id);
}
