package com.waveaccess.tacocloud.services;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(String id){
        return ingredientRepository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        ingredientRepository.deleteById(id);
    }

    @Transactional
    public void saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }
}
