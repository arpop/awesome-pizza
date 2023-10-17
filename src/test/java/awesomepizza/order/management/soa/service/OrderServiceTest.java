package awesomepizza.order.management.soa.service;


import awesomepizza.order.management.soa.OrderStatus;
import awesomepizza.order.management.soa.entity.Order;
import awesomepizza.order.management.soa.exception.ResourceNotFoundException;
import awesomepizza.order.management.soa.exceptions.OrderAlreadyPlacedException;
import awesomepizza.order.management.soa.exceptions.OrderNotCompliantException;
import awesomepizza.order.management.soa.exceptions.OrderSequenceViolationException;
import awesomepizza.order.management.soa.mapper.OrderMapper;
import awesomepizza.order.management.soa.mapper.OrderMapperImpl;
import awesomepizza.order.management.soa.mapper.PizzaOrderMapper;
import awesomepizza.order.management.soa.mapper.PizzaOrderMapperImpl;
import awesomepizza.order.management.soa.model.OrderModel;
import awesomepizza.order.management.soa.model.PizzaOrderModel;
import awesomepizza.order.management.soa.repository.OrderRepository;
import awesomepizza.order.management.soa.repository.PizzaOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PizzaOrderRepository pizzaOrderRepository;
    @Spy
    private OrderMapper orderMapper = new OrderMapperImpl();
    @Spy
    private PizzaOrderMapper pizzaOrderMapper = new PizzaOrderMapperImpl();

    @InjectMocks
    private OrderService service;

    @Test
    void testGetAllOrders() {
        service.getAllOrders();
        verify(orderRepository, times(1)).getAllOrders();
    }

    @Test
    void testGetById() {
        var order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.PLACED)
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        var pizzaModel = service.getById(1L);
        assertThat(pizzaModel).isNotNull();
        assertThat(pizzaModel.getOrderStatus()).isEqualTo(OrderStatus.PLACED);
    }

    @Test
    void testGetByIdException() {
        when(orderRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetNextOrder() {
        service.getNextOrder();
        verify(orderRepository, times(1)).getNextOrder();
    }

    @Test
    void testPlaceOrder() {
        var orderTime = LocalDateTime.of(2023, 10, 16, 23, 40);
        var orderModel = OrderModel.builder()
                .placementTime(orderTime)
                .pizzaList(new ArrayList<>())
                .build();
        orderModel.getPizzaList().add(PizzaOrderModel.builder().build());
        when(orderRepository.save(any())).thenReturn(Order.builder().id(7L).placementTime(orderTime).build());
        var order = service.placeOrder(orderModel);
        assertThat(order).isNotNull().isEqualTo(7L);
    }

    @ParameterizedTest
    @MethodSource("placeOrderExceptionCases")
    void testPlaceOrderExceptions(OrderModel orderModel, Class<?> exceptionType) {
        assertThatThrownBy(() -> service.placeOrder(orderModel)).isInstanceOf(exceptionType);
    }

    public static Stream<Arguments> placeOrderExceptionCases() {
        return Stream.of(
                Arguments.of(OrderModel.builder().id(1L).build(), OrderAlreadyPlacedException.class),
                Arguments.of(OrderModel.builder().orderStatus(OrderStatus.IN_PROGRESS).build(), OrderNotCompliantException.class),
                Arguments.of(OrderModel.builder().orderStatus(OrderStatus.PLACED).build(), OrderNotCompliantException.class)
        );
    }

    @Test
    void testPickOrder() {
        when(orderRepository.getNextOrder()).thenReturn(Order.builder().id(7L).build());
        var order = service.pickOrder(7L);
        assertThat(order).isNotNull();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
    }

    @Test
    void testPickOrderException() {
        when(orderRepository.getNextOrder()).thenReturn(Order.builder().id(7L).build());
        assertThatThrownBy(() -> service.pickOrder(6L)).isInstanceOf(OrderSequenceViolationException.class);
    }

    @Test
    void testUpdate() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(Order.builder().id(1L).build()));
        when(orderRepository.save(any())).thenReturn(Order.builder().id(3L).build());
        var order = service.update(OrderModel.builder().id(1L).build());
        verify(orderRepository, times(1)).save(any());
        assertThat(order).isNotNull().returns(3L, value -> order.getId());
    }

    @Test
    void testUpdateException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(OrderModel.builder().id(1L).build())).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testDelete() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(Order.builder().build()));
        service.deleteOrder(1L);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testDeleteException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.deleteOrder(1L)).isInstanceOf(ResourceNotFoundException.class);
    }
}