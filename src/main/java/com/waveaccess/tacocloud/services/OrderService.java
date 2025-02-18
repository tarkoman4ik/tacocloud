package com.waveaccess.tacocloud.services;

import com.waveaccess.tacocloud.models.Order;
import com.waveaccess.tacocloud.models.Taco;
import com.waveaccess.tacocloud.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public void saveDesign(Order order, Taco design){
        order.addDesign(design);
    }
}
