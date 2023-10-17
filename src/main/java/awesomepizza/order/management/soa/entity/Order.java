package awesomepizza.order.management.soa.entity;

import awesomepizza.order.management.soa.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placementTime;
    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus orderStatus;
    private int tableNumber;
    @OneToMany(mappedBy = "order")
    @Setter
    private List<PizzaOrder> pizzaList;
}
