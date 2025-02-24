package com.waveaccess.tacocloud.controllers;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.models.Order;
import com.waveaccess.tacocloud.models.Taco;
import com.waveaccess.tacocloud.services.IngredientService;
import com.waveaccess.tacocloud.services.OrderService;
import com.waveaccess.tacocloud.services.TacoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    private final OrderService orderService;

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientService.findIngredients().forEach(ingredients::add);
        Map<Type, List<Ingredient>> mapTypes = new HashMap<>();
        for (Ingredient i : ingredients) {
            List<Ingredient> list = new ArrayList<>();
            if (mapTypes.get(i.getType()) != null)
                list = mapTypes.get(i.getType());
            list.add(i);
            mapTypes.put(i.getType(), list);
        }
        for (var x : mapTypes.entrySet()) {
            model.addAttribute(x.getKey().toString().toLowerCase(), x.getValue());
        }
        model.addAttribute("design", new Taco());
        return "designForm";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "designForm";
        }
        orderService.saveDesign(order,tacoService.saveTaco(design));
        log.info("Обработка дизайна: " + design);
        return "redirect:/orders/current";
    }
}
