package com.waveaccess.tacocloud;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min=5,message="Минимальная длина имени - 5 символов")
    private String name;

    @ToString.Exclude
    @ManyToMany(targetEntity=Ingredient.class)
    @JoinTable(name="Taco_Ingredients", joinColumns = { @JoinColumn(name="taco") },
            inverseJoinColumns = { @JoinColumn(name="ingredient") })
    @Size(min=1,message="Должен быть выбран хотя б один ингредиент")
    private List<Ingredient> ingredients = new ArrayList<>();

    private Date createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }

}
