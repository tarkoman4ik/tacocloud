package com.waveaccess.tacocloud;

import com.waveaccess.tacocloud.models.Order;
import com.waveaccess.tacocloud.repositories.OrderRepository;
import com.waveaccess.tacocloud.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTestsOrderService {

    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    OrderService orderService;

    @Test
    public void givenOrder_MockSave_SuccessSavingOrder(){
        Order order = new Order();
        order.setName("asdsdfasd");
        order.setCity("sddasdasd");
        order.setState("asdasfas");
        order.setCcCVV("123");
        order.setCcNumber("111111111111");
        order.setZip("sfadsfasd");
        order.setCcExpiration("12/28");
        when(orderRepository.save(order)).thenReturn(order);
        orderService.saveOrder(order);
        verify(orderRepository,times(1)).save(order);
    }

   @Test
   public void givenNullObject_MockSave_ThrowRuntimeExceptionNullObject(){
        when(orderRepository.save(any())).thenReturn(Optional.empty());
        assertThatThrownBy(()->orderService.saveOrder(new Order()));
   }
}
