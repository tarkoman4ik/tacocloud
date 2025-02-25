package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import com.waveaccess.tacocloud.services.IngredientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = "spring.flyway.enabled=false")
public class IntegrationTestsIngredientRepository {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private IngredientRepository ingredientRepository;

    @TestConfiguration
    static class TestConfig{
        @Bean
        public IngredientService ingredientService(IngredientRepository ingredientRepository){
            return new IngredientService(ingredientRepository);
        }
    }

    @Test
    public void saveNewIngredient() {
        Ingredient ingredient = new Ingredient("DATA", "Тест лепешка", Type.WRAP);
        ingredientService.saveIngredient(ingredient);
        Ingredient savedIngredient = ingredientService.getById(ingredient.getId());
        assertNotNull(savedIngredient);
        assertEquals(ingredient, savedIngredient);
    }

    @Test
    public void findNullIngredient() {
        Ingredient ingredient = new Ingredient("DATA", "Тест лепешка", Type.WRAP);
        ingredientService.saveIngredient(ingredient);
        Ingredient foundIngredient = ingredientService.getById("DATA2");
        assertNull(foundIngredient);
    }

    @Test
    public void findExistIngredient() {
        Ingredient ingredient = new Ingredient("DATA", "Тест лепешка", Type.WRAP);
        ingredientService.saveIngredient(ingredient);
        Ingredient foundIngredient = ingredientService.getById("DATA");
        assertEquals(ingredient, foundIngredient);
    }

    @Test
    public void findAllIngredients() {
        ingredientService.saveIngredient(new Ingredient("TEST1", "ТЕСТ", Type.WRAP));
        ingredientService.saveIngredient(new Ingredient("TEST2", "TEST", Type.CHEESE));
        ingredientService.saveIngredient(new Ingredient("TEST3", "TEST", Type.PROTEIN));
        assertEquals(3, ingredientService.findIngredients().size());
    }

    @Test
    public void updateIngredientById() {
        Ingredient ingredient = new Ingredient("TEST1", "TEST", Type.WRAP);
        ingredientService.saveIngredient(ingredient);
        Ingredient updatableIngredient = ingredientService.getById("TEST1");
        updatableIngredient.setName("Тесто");
        updatableIngredient.setType(Type.PROTEIN);
        ingredientService.saveIngredient(updatableIngredient);
        Ingredient chekingIngredient = ingredientService.getById("TEST1");
        assertEquals(chekingIngredient, updatableIngredient);
    }

    @Test
    public void deleteIngredientById() {
        Ingredient ingredient1 = new Ingredient("TEST1", "TEST", Type.WRAP);
        ingredientService.saveIngredient(ingredient1);
        Ingredient ingredient2 = new Ingredient("TEST2", "TEST", Type.WRAP);
        ingredientService.saveIngredient(ingredient2);
        Ingredient ingredient3 = new Ingredient("TEST3", "TEST", Type.WRAP);
        ingredientService.saveIngredient(ingredient3);
        ingredientService.deleteById(ingredient2.getId());
        Iterable ingredients = ingredientRepository.findAll();
        assertThat(ingredients).hasSize(2).contains(ingredient1, ingredient3);
    }

    @Test
    public void findZeroIngredientsIfRepoIsEmpty() {
        Iterable ingredients = ingredientService.findIngredients();
        assertThat(ingredients).hasSize(0).isEmpty();
    }
}
