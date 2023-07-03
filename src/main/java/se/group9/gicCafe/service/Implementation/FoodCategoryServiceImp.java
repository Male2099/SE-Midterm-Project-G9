package se.group9.gicCafe.service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import se.group9.gicCafe.model.FoodCategory;
import se.group9.gicCafe.repository.FoodCategoryRepo;
import se.group9.gicCafe.service.FoodCategoryService;


@Service
public class FoodCategoryServiceImp implements FoodCategoryService{
    private FoodCategoryRepo foodCategoryRepo;

    public FoodCategoryServiceImp(FoodCategoryRepo foodCategoryRepo) {
        super();
        this.foodCategoryRepo = foodCategoryRepo;
    }

    @Override
    public List<FoodCategory> getAllFoodCategories() {
        return foodCategoryRepo.findAll();
     }

    @Override
    public FoodCategory getFoodCategoryByID(int id) {
        return foodCategoryRepo.getReferenceById(id);
    }



    @Override
    public FoodCategory saveFoodCategory(FoodCategory foodCategory) {
        return foodCategoryRepo.save(foodCategory);
    }
}
