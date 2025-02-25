package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import com.waveaccess.tacocloud.services.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTestsIngredientService {

    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private IngredientService ingredientService;

    @Test
    public void givenOptionalEmpty_MockFindById_ThrowRuntimeException() {
        when(ingredientRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ingredientService.getNameById("EmptyName"));
    }

    @Test
    public void givenIngredient_MockFindById_FoundObjectInService() {
        Ingredient ingredient = new Ingredient("FLTO", "Пшеничная лепешка", Type.WRAP);
        when(ingredientRepository.findById("FLTO")).thenReturn(Optional.of(ingredient));
        ingredientService.saveIngredient(ingredient);
        Assertions.assertEquals("Пшеничная лепешка", ingredientService.getNameById("FLTO"));
    }

    @Test
    public void givenListIngredient_MockFindAll_SuccessFoundListIngredients() {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Пшеничная  лепешка", Type.WRAP),
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
        when(ingredientRepository.findAll()).thenReturn(ingredients);
        Assertions.assertEquals(ingredients, ingredientService.findIngredients());
    }

    @Test
    public void givenIngredient_MockSave_VerifyIngredientIsSavedSuccessful() {
        Ingredient ingredient = new Ingredient("CARN", "Свинина", Type.PROTEIN);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        ingredientService.saveIngredient(ingredient);
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    public void givenNullObject_MockSave_ThrowRuntimeExceptionNullObject() {
        when(ingredientRepository.save(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ingredientService.saveIngredient(new Ingredient("FLTO", "Пшеничная лепешка", Type.WRAP)));
    }
}
