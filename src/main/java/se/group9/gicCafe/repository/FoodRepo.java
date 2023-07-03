package se.group9.gicCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.group9.gicCafe.model.Food;

public interface FoodRepo extends JpaRepository<Food, Integer> {
    
}
