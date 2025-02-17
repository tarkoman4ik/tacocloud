package com.waveaccess.tacocloud.services;

import com.waveaccess.tacocloud.models.Taco;
import com.waveaccess.tacocloud.repositories.TacoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TacoService {
    private final TacoRepository tacoRepository;

    @Transactional
    public Taco saveTaco(Taco taco){
        tacoRepository.save(taco);
        return taco;
    }
}
