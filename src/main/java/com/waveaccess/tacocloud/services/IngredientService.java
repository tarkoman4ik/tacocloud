package com.waveaccess.tacocloud.services;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findIngredients() {
        return ingredientRepository.findAll();
    }

    public String getNameById(String id){
        return ingredientRepository.findById(id).orElseThrow(()-> new RuntimeException("Null object")).getName();
    }

    @Transactional
    public void saveIngredient(Ingredient ingredient) {
        if (ingredient!=null) {
            ingredientRepository.save(ingredient);
        }
        else{
            throw new RuntimeException("Null object");
        }
    }
}
