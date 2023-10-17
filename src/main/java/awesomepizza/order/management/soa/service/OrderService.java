package awesomepizza.order.management.soa.service;

import awesomepizza.order.management.soa.OrderStatus;
import awesomepizza.order.management.soa.exception.ResourceNotFoundException;
import awesomepizza.order.management.soa.exceptions.OrderAlreadyPlacedException;
import awesomepizza.order.management.soa.exceptions.OrderNotCompliantException;
import awesomepizza.order.management.soa.exceptions.OrderSequenceViolationException;
import awesomepizza.order.management.soa.mapper.OrderMapper;
import awesomepizza.order.management.soa.mapper.PizzaOrderMapper;
import awesomepizza.order.management.soa.model.OrderModel;
import awesomepizza.order.management.soa.repository.OrderRepository;
import awesomepizza.order.management.soa.repository.PizzaOrderRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PizzaOrderMapper pizzaOrderMapper;

    public List<OrderModel> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(orderMapper::entityToModel).toList();
    }

    public OrderModel getById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return orderMapper.entityToModel(order);
    }

    public OrderModel getNextOrder() {
        return orderMapper.entityToModel(orderRepository.getNextOrder());
    }

    @SneakyThrows
    public Long placeOrder(OrderModel orderModel) {
        if (orderModel.getId() != null) {
            throw new OrderAlreadyPlacedException();
        }

        if (orderModel.getOrderStatus() == null) {
            orderModel.setOrderStatus(OrderStatus.PLACED);
        }

        if (orderModel.getOrderStatus() != OrderStatus.PLACED
                || CollectionUtils.isEmpty(orderModel.getPizzaList())) {
            throw new OrderNotCompliantException();
        }

        var order = orderRepository.save(orderMapper.modelToEntity(orderModel));

        var pizzaOrderList = orderModel.getPizzaList().stream()
                .peek(model -> model.setOrderId(order.getId()))
                .map(pizzaOrderMapper::modelToEntity)
                .toList();
        pizzaOrderRepository.saveAll(pizzaOrderList);
        return order.getId();
    }

    public void deleteOrder(Long id) {
        var order = orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @SneakyThrows
    public OrderModel pickOrder(Long id) {
        var order = orderRepository.getNextOrder();
        if (!order.getId().equals(id)) {
            throw new OrderSequenceViolationException();
        }

        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
        return orderMapper.entityToModel(order);
    }

    @SneakyThrows
    public OrderModel update(OrderModel orderModel) {
        if (orderRepository.findById(orderModel.getId()).isEmpty()) {
            throw new ResourceNotFoundException();
        }

        var updatedOrder = orderRepository.save(orderMapper.modelToEntity(orderModel));
        return orderMapper.entityToModel(updatedOrder);
    }
}
