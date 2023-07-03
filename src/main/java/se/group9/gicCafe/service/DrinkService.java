package se.group9.gicCafe.service;

import java.util.List;

import se.group9.gicCafe.model.Drink;

public interface DrinkService {
    List<Drink> getAllDrinks();   

    Drink saveDrink(Drink Drink);
	
    Drink getDrinkById(int id);
    
    Drink updateDrink(Drink Drink);
	
	void deleteDrinkById(int id);
    
// ------
    Drink saveAndFlushDrink(Drink Drink);

    double getDrinkPriceBySize(Drink drink, String size);
}
