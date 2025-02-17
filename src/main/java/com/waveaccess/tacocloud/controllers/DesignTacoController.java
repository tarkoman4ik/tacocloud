package com.waveaccess.tacocloud.controllers;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.models.Taco;
import com.waveaccess.tacocloud.models.Order;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import com.waveaccess.tacocloud.repositories.TacoRepository;
import com.waveaccess.tacocloud.services.IngredientService;
import com.waveaccess.tacocloud.services.TacoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientService ingredientService;

    private final TacoService tacoService;

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientService.findIngredients().forEach(ingredients::add);
        Type[] types = Type.values();
        Map<Type,List<Ingredient>> mapTypes = new HashMap<>();

        //TODO: ingridient map(type,List<Ingrediet>)
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    private Object filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).toList();
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        //TODO: add to service
        Taco saved = tacoService.saveTaco(design);
        order.addDesign(saved);
        log.info("Обработка дизайна: " + design);
        return "redirect:/orders/current";
    }
}
