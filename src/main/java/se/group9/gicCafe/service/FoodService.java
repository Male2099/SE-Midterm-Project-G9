package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.Food;

public interface FoodService {
    List<Food> getAllFoods();


    Food saveFood(Food food);

    Food getFoodById(int id);

    Food updateFood(Food food);
	
	void deleteFoodById(int id);
}
