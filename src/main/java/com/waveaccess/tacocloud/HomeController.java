package com.waveaccess.tacocloud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.waveaccess.tacocloud.Ingredient.Type;

@Controller
public class HomeController {

    private final IngredientRepository ingredientRepository;

    public HomeController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/")
    public String home(){
        ingredientRepository.save(new Ingredient("FLTO","Пшеничная лепешка", Type.WRAP));
        ingredientRepository.save(new Ingredient("COTO", "Кукурузная лепешка", Type.WRAP));
        ingredientRepository.save(new Ingredient("GRBF", "Говядина", Type.PROTEIN));
        ingredientRepository.save(new Ingredient("CARN", "Свинина", Type.PROTEIN));
        ingredientRepository.save(new Ingredient("TMTO", "Помидоры", Type.VEGGIES));
        ingredientRepository.save(new Ingredient("LETC", "Салат", Type.VEGGIES));
        ingredientRepository.save(new Ingredient("CHED", "Чеддер", Type.CHEESE));
        ingredientRepository.save(new Ingredient("JACK", "Джек", Type.CHEESE));
        ingredientRepository.save(new Ingredient("SLSA", "Сальса", Type.SAUCE));
        ingredientRepository.save( new Ingredient("SRCR", "Сметана", Type.SAUCE));
        return "home";
    }
}
