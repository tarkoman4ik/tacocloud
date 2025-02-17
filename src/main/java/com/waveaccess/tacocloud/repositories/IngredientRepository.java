package com.waveaccess.tacocloud.repositories;

import com.waveaccess.tacocloud.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,String> {

}
