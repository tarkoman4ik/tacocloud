package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.Ingredient.Type;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Пшеничная лепешка", Type.WRAP),
                new Ingredient("COTO", "Кукурузная лепешка", Type.WRAP),
                new Ingredient("GRBF", "Говядина", Type.PROTEIN),
                new Ingredient("CARN", "Свинина", Type.PROTEIN),
                new Ingredient("TMTO", "Помидоры", Type.VEGGIES),
                new Ingredient("LETC", "Салат", Type.VEGGIES),
                new Ingredient("CHED", "Чеддер", Type.CHEESE),
                new Ingredient("JACK", "Джек", Type.CHEESE),
                new Ingredient("SLSA", "Сальса", Type.SAUCE),
                new Ingredient("SRCR", "Сметана", Type.SAUCE)
        );
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model){
        model.addAttribute("design", new Taco());
        return "design";
    }

    private Object filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).toList();
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Обработка дизайна: " + design);
        return "redirect:/orders/current";
    }
}
