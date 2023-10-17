package awesomepizza.order.management.soa.model;

import awesomepizza.order.management.soa.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class OrderModel {
    private Long id;
    private LocalDateTime placementTime;
    @Setter
    private OrderStatus orderStatus;
    private int tableNumber;
    private List<PizzaOrderModel> pizzaList;
}
