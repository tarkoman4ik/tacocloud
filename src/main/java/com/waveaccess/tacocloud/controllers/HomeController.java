package com.waveaccess.tacocloud.controllers;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import com.waveaccess.tacocloud.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.waveaccess.tacocloud.models.Ingredient.Type;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final IngredientService ingredientService;

    @GetMapping("/")
    public String home(){
        ingredientService.saveIngredient(new Ingredient("FLTO","Пшеничная лепешка", Type.WRAP));
        ingredientService.saveIngredient(new Ingredient("COTO", "Кукурузная лепешка", Type.WRAP));
        ingredientService.saveIngredient(new Ingredient("GRBF", "Говядина", Type.PROTEIN));
        ingredientService.saveIngredient(new Ingredient("CARN", "Свинина", Type.PROTEIN));
        ingredientService.saveIngredient(new Ingredient("TMTO", "Помидоры", Type.VEGGIES));
        ingredientService.saveIngredient(new Ingredient("LETC", "Салат", Type.VEGGIES));
        ingredientService.saveIngredient(new Ingredient("CHED", "Чеддер", Type.CHEESE));
        ingredientService.saveIngredient(new Ingredient("JACK", "Джек", Type.CHEESE));
        ingredientService.saveIngredient(new Ingredient("SLSA", "Сальса", Type.SAUCE));
        ingredientService.saveIngredient( new Ingredient("SRCR", "Сметана", Type.SAUCE));
        return "home";
    }
}
