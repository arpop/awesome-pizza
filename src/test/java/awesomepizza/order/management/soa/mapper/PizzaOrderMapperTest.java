package awesomepizza.order.management.soa.mapper;

import awesomepizza.order.management.soa.entity.Order;
import awesomepizza.order.management.soa.entity.Pizza;
import awesomepizza.order.management.soa.entity.PizzaOrder;
import awesomepizza.order.management.soa.model.PizzaOrderModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PizzaOrderMapperTest {

    private final PizzaOrderMapper mapper = new PizzaOrderMapperImpl();

    @Spy
    private PizzaMapper pizzaMapper = new PizzaMapperImpl();

    @Spy
    private OrderMapper orderMapper = new OrderMapperImpl();

    @Test
    void testEntityToModel() {
        var pizza = Pizza.builder().id(1L).name("Margherita").ingredients("Ingredients").build();
        var order = Order.builder().id(3L)
                .placementTime(LocalDateTime.of(2023, 10, 17, 22, 45))
                .build();
        var pizzaOrder = PizzaOrder.builder().pizza(pizza).order(order).build();
        var model = mapper.entityToModel(pizzaOrder);
        assertThat(model).isNotNull()
                .returns(pizza.getId(), pizzaId -> model.getPizzaId())
                .returns(order.getId(), orderId -> model.getOrderId())
                .returns(pizza.getName(), pizzaName -> model.getPizzaName());
    }

    @Test
    void testModelToEntity() {
        var pizzaOrderModel = PizzaOrderModel.builder()
                .orderId(3L)
                .pizzaId(1L)
                .pizzaName("Margherita")
                .note("note")
                .build();
        var entity = mapper.modelToEntity(pizzaOrderModel);
        assertThat(entity).isNotNull()
                .returns(1L, pizza -> entity.getPizza().getId())
                .returns(null, pizza -> entity.getPizza().getName())
                .returns(3L, order -> entity.getOrder().getId())
                .returns("note", note -> entity.getNote());
    }

    @Test
    void testNulls() {
        assertThat(mapper.entityToModel(null)).isNull();
        assertThat(mapper.modelToEntity(null)).isNull();
    }
}