package awesomepizza.order.management.soa.controller;

import awesomepizza.order.management.soa.model.OrderModel;
import awesomepizza.order.management.soa.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController controller;

    @Test
    void getAllOrders() {
        controller.getAllOrders();
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getById() {
        controller.getById(1L);
        verify(orderService, times(1)).getById(1L);
    }

    @Test
    void pickOrder() {
        controller.pickOrder(1L);
        verify(orderService, times(1)).pickOrder(1L);
    }

    @Test
    void getNext() {
        controller.getNext();
        verify(orderService, times(1)).getNextOrder();
    }

    @Test
    void delete() {
        controller.delete(1L);
        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void update() {
        var order = OrderModel.builder().build();
        controller.update(order);
        verify(orderService, times(1)).update(order);
    }

    @Test
    void place() {
        var order = OrderModel.builder().build();
        controller.place(order);
        verify(orderService, times(1)).placeOrder(order);
    }
}