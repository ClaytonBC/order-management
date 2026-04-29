package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateOrderRequest;
import com.clayton.ordermanagementapi.dto.OrderItemRequest;
import com.clayton.ordermanagementapi.dto.OrderResponse;
import com.clayton.ordermanagementapi.entity.Order;
import com.clayton.ordermanagementapi.entity.Product;
import com.clayton.ordermanagementapi.enums.Status;
import com.clayton.ordermanagementapi.exception.BusinessException;
import com.clayton.ordermanagementapi.exception.ResourceNotFoundException;
import com.clayton.ordermanagementapi.repository.OrderRepository;
import com.clayton.ordermanagementapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldReturnOrderById() {

        Order order = new Order();
        order.setId(1L);
        order.setCustomer("test@email.com");
        order.setStatus(Status.RECEIVED);
        order.setItems(new ArrayList<>());
        order.setTotalPrice(BigDecimal.valueOf(100));

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        OrderResponse response = orderService.findById(1L);

        assertEquals(1L, response.id());
        assertEquals("test@email.com", response.customer());
        assertEquals(Status.RECEIVED, response.status());

        verify(orderRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                orderService.findById(1L)
        );

        verify(orderRepository).findById(1L);
    }

    @Test
    void shouldReturnOrdersByCustomer() {

        Order order = new Order();
        order.setId(1L);
        order.setCustomer("test@email.com");
        order.setStatus(Status.RECEIVED);
        order.setItems(new ArrayList<>());
        order.setTotalPrice(BigDecimal.valueOf(50));

        when(orderRepository.findByCustomer("test@email.com"))
                .thenReturn(List.of(order));

        List<OrderResponse> response = orderService.findAll("test@email.com");

        assertEquals(1, response.size());
        assertEquals("test@email.com", response.get(0).customer());

        verify(orderRepository).findByCustomer("test@email.com");
    }

    @Test
    void shouldReturnAllOrders() {

        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomer("user1@email.com");
        order1.setStatus(Status.RECEIVED);
        order1.setItems(new ArrayList<>());
        order1.setTotalPrice(BigDecimal.valueOf(50));

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomer("user2@email.com");
        order2.setStatus(Status.PREPARING);
        order2.setItems(new ArrayList<>());
        order2.setTotalPrice(BigDecimal.valueOf(100));

        when(orderRepository.findAll())
                .thenReturn(List.of(order1, order2));

        List<OrderResponse> response = orderService.findAllOrders();

        assertEquals(2, response.size());
        assertEquals("user1@email.com", response.get(0).customer());
        assertEquals("user2@email.com", response.get(1).customer());

        verify(orderRepository).findAll();
    }

    @Test
    void shouldUpdateStatusSuccessfully() {

        Order order = new Order();
        order.setId(1L);
        order.setStatus(Status.RECEIVED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order updated = orderService.updateStatus(1L, Status.PREPARING);

        assertEquals(Status.PREPARING, updated.getStatus());

        verify(orderRepository).save(order);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingOrder() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                orderService.updateStatus(1L, Status.PREPARING)
        );

        verify(orderRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenInvalidStatusTransition() {

        Order order = new Order();
        order.setId(1L);
        order.setStatus(Status.DELIVERED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(BusinessException.class, () ->
                orderService.updateStatus(1L, Status.RECEIVED)
        );

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldCreateOrderSuccessfully() {

        Product product = new Product();
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(10));

        OrderItemRequest itemRequest = new OrderItemRequest(1L, 2);
        CreateOrderRequest request = new CreateOrderRequest(List.of(itemRequest));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.createOrder(request, "test@email.com");

        assertEquals("test@email.com", response.customer());
        assertEquals(Status.RECEIVED, response.status());
        assertEquals(1, response.items().size());
        assertEquals(BigDecimal.valueOf(20), response.totalPrice()); // 10 * 2

        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowWhenProductNotFound() {

        OrderItemRequest itemRequest = new OrderItemRequest(1L, 2);
        CreateOrderRequest request = new CreateOrderRequest(List.of(itemRequest));

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                orderService.createOrder(request, "test@email.com")
        );

        verify(productRepository).findById(1L);
        verify(orderRepository, never()).save(any());
    }
}
