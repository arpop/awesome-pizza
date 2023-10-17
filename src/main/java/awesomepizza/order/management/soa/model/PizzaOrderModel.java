package awesomepizza.order.management.soa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
public class PizzaOrderModel {
    @Setter
    private Long orderId;
    private Long pizzaId;
    private String pizzaName;
    private String note;

}
