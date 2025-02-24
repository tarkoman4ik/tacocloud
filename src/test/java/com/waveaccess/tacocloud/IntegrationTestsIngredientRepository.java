package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = "spring.flyway.enabled=false")
public class IntegrationTestsIngredientRepository {
    @Autowired
    private IngredientRepository ingredientRepository;
    private Ingredient ingredient;

    @Test
    public void saveNewIngredient() {
        ingredient = new Ingredient("DATA", "Тест лепешка", Type.WRAP);
        ingredientRepository.save(ingredient);
        Ingredient savedIngredient = ingredientRepository.findById(ingredient.getId()).orElse(null);
        assertNotNull(savedIngredient);
        assertEquals(ingredient, savedIngredient);
    }

    @Test
    public void findNullIngredient(){
        ingredient = new Ingredient("DATA", "Тест лепешка", Type.WRAP);
        ingredientRepository.save(ingredient);
        Ingredient foundIngredient = ingredientRepository.findById("DATA2").orElse(null);
        assertNull(foundIngredient);
    }

    @Test
    public void findExistIngredient(){
        ingredient = new Ingredient("DATA", "Тест лепешка", Type.WRAP);
        ingredientRepository.save(ingredient);
        Ingredient foundIngredient = ingredientRepository.findById("DATA").orElse(null);
        assertEquals(ingredient,foundIngredient);
    }

    @Test
    public void findAllIngredients() {
        ingredientRepository.save(new Ingredient("TEST1","ТЕСТ",Type.WRAP));
        ingredientRepository.save(new Ingredient("TEST2","TEST",Type.CHEESE));
        ingredientRepository.save(new Ingredient("TEST3","TEST",Type.PROTEIN));
        assertEquals(3,ingredientRepository.findAll().size());
    }

    @Test
    public void updateIngredientById(){
        ingredient = new Ingredient("TEST1","TEST", Type.WRAP);
        ingredientRepository.save(ingredient);
        Ingredient updatableIngredient = ingredientRepository.findById("TEST1").orElse(null);
        updatableIngredient.setName("Тесто");
        updatableIngredient.setType(Type.PROTEIN);
        ingredientRepository.save(updatableIngredient);
        Ingredient chekingIngredient = ingredientRepository.findById("TEST1").orElse(null);
        assertEquals(chekingIngredient,updatableIngredient);
    }

    @Test
    public void deleteIngredientById(){
        Ingredient ingredient1 = new Ingredient("TEST1","TEST",Type.WRAP);
        ingredientRepository.save(ingredient1);
        Ingredient ingredient2 = new Ingredient("TEST2","TEST",Type.WRAP);
        ingredientRepository.save(ingredient2);
        Ingredient ingredient3 = new Ingredient("TEST3","TEST",Type.WRAP);
        ingredientRepository.save(ingredient3);
        ingredientRepository.deleteById(ingredient2.getId());
        Iterable ingredients = ingredientRepository.findAll();
        assertThat(ingredients).hasSize(2).contains(ingredient1,ingredient3);
    }

    @Test
    public void findZeroIngredientsIfRepoIsEmpty(){
        Iterable ingredients = ingredientRepository.findAll();
        assertThat(ingredients).hasSize(0).isEmpty();
    }
}
