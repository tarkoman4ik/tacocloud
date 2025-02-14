package com.waveaccess.tacocloud;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class Taco {
    @NotNull
    @Size(min=5,message="Минимальная длина имени - 5 символов")
    private String name;
    @Size(min=1,message="Должен быть выбран хотя б один ингредиент")
    private List<String> ingredients;
}
