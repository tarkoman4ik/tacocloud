package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.models.Ingredient;
import com.waveaccess.tacocloud.models.Ingredient.Type;
import com.waveaccess.tacocloud.models.Taco;
import com.waveaccess.tacocloud.repositories.TacoRepository;
import com.waveaccess.tacocloud.services.TacoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTestsTacoService {

    @Mock
    TacoRepository tacoRepository;
    @InjectMocks
    TacoService tacoService;

    @Test
    public void givenTaco_MockSave_SuccessSavedNewTaco() {
        Taco taco = new Taco();
        taco.setId(1L);
        taco.setName("Вкуснятина");
        taco.setIngredients(List.of(new Ingredient("FLTO", "Пшеничная лепешка", Type.WRAP), new Ingredient("TMTO", "Помидоры", Type.VEGGIES)));
        when(tacoRepository.save(taco)).thenReturn(taco);
        tacoService.saveTaco(taco);
        verify(tacoRepository, times(1)).save(taco);
    }

    @Test
    public void givenNullObject_MockSave_ThrowRuntimeExceptionNullObject() {
        when(tacoRepository.save(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tacoService.saveTaco(new Taco()));
    }
}
