package se.group9.gicCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.group9.gicCafe.model.FoodCategory;

public interface FoodCategoryRepo extends JpaRepository<FoodCategory, Integer> {
    
}
