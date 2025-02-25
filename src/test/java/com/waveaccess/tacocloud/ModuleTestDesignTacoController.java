package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.controllers.DesignTacoController;
import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ModuleTestDesignTacoController {
    @Mock
    private IngredientService ingredientService;
    @InjectMocks
    private DesignTacoController designTacoController;

    private MockMvc mockMvc;
    private List<Ingredient> ingredients;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(designTacoController).build();
        ingredients = Arrays.asList(
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
        when(ingredientService.findIngredients()).thenReturn(ingredients);
    }

    @Test
    public void testReturnDesignFormWithIngredientAttribute() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("designForm"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }

}
