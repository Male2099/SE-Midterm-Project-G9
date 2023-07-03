package se.group9.gicCafe.service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import se.group9.gicCafe.model.Food;
import se.group9.gicCafe.repository.FoodRepo;
import se.group9.gicCafe.service.FoodService;

@Service
public class FoodServiceImp implements FoodService {
    private FoodRepo foodRepo;

    public FoodServiceImp(FoodRepo foodRepo) {
        super();
        this.foodRepo = foodRepo;
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepo.findAll();
    }

    public Food saveFood(Food food) {
        return foodRepo.save(food);
    }

    @Override
    public Food getFoodById(int id) {
		return foodRepo.findById(id).get();
    }

    @Override
	public Food updateFood(Food food) {
		return foodRepo.save(food);
	}

	@Override
	public void deleteFoodById(int id) {
		foodRepo.deleteById(id);	
	}
}
