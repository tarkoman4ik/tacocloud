package com.waveaccess.tacocloud.repositories;

import com.waveaccess.tacocloud.models.Taco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoRepository extends JpaRepository<Taco,Long> {

}
