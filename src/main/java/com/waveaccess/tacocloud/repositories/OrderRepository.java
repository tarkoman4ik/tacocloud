package com.waveaccess.tacocloud.repositories;

import com.waveaccess.tacocloud.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
