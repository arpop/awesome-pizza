package awesomepizza.order.management.soa.mapper;

import awesomepizza.order.management.soa.entity.Pizza;
import awesomepizza.order.management.soa.model.PizzaModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    Pizza modelToEntity(PizzaModel model);

    PizzaModel entityToModel(Pizza entity);
}
