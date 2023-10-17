package awesomepizza.order.management.soa.mapper;

import awesomepizza.order.management.soa.entity.PizzaOrder;
import awesomepizza.order.management.soa.model.PizzaOrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PizzaOrderMapper {
    @Mapping(target = "pizza.id", source = "pizzaId")
    @Mapping(ignore = true, target = "pizza.name", source = "pizzaName")
    @Mapping(target = "order.id", source = "orderId")
    PizzaOrder modelToEntity(PizzaOrderModel model);

    @Mapping(target = "pizzaId", source = "pizza.id")
    @Mapping(target = "pizzaName", source = "pizza.name")
    @Mapping(target = "orderId", source = "order.id")
    PizzaOrderModel entityToModel(PizzaOrder entity);
}
