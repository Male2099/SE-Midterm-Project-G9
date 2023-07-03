package se.group9.gicCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.group9.gicCafe.model.Drink;

public interface DrinkRepo extends JpaRepository<Drink, Integer> {

}