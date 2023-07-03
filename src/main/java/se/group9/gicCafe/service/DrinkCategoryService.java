package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.DrinkCategory;

public interface DrinkCategoryService {
    List<DrinkCategory> getAllDrinkCategories();   

    DrinkCategory savAndFlushDrinkCategory(DrinkCategory drinkCategory);

    DrinkCategory getDrinkCategoryByID(int id);
    
    DrinkCategory saveDrinkCategory(DrinkCategory drinkCategory);
}
