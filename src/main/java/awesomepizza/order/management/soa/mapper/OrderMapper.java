package awesomepizza.order.management.soa.mapper;

import awesomepizza.order.management.soa.entity.Order;
import awesomepizza.order.management.soa.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PizzaOrderMapper.class)
public interface OrderMapper {
    @Mapping(ignore = true, target = "pizzaList")
    Order modelToEntity(OrderModel model);
    OrderModel entityToModel(Order entity);
}
