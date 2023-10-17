package awesomepizza.order.management.soa.controller;

import awesomepizza.order.management.soa.model.OrderModel;
import awesomepizza.order.management.soa.service.OrderService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("{id}")
    public OrderModel getById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @PatchMapping(path = "pick/{id}")
    public OrderModel pickOrder(@PathVariable("id") Long id) {
        return orderService.pickOrder(id);
    }

    @GetMapping("next")
    public OrderModel getNext() {
        return orderService.getNextOrder();
    }

    @DeleteMapping
    public void delete(Long id) {
        orderService.deleteOrder(id);
    }

    @PutMapping
    public OrderModel update(@RequestBody OrderModel orderModel) {
        return orderService.update(orderModel);
    }

    @PostMapping
    public Long place(@RequestBody OrderModel orderModel) {
        return orderService.placeOrder(orderModel);
    }
}
