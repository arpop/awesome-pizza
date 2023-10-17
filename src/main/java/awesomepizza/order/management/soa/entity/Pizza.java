package awesomepizza.order.management.soa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ingredients;
    @OneToMany(mappedBy = "pizza")
    private List<PizzaOrder> pizzaOrders;
    @Setter
    private Boolean deleted;

    @PrePersist
    void preInsert() {
        if (this.deleted == null)
            this.deleted = false;
    }
}
