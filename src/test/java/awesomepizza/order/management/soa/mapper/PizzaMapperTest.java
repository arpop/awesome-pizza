package awesomepizza.order.management.soa.mapper;

import awesomepizza.order.management.soa.entity.Pizza;
import awesomepizza.order.management.soa.model.PizzaModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaMapperTest {
    private final PizzaMapper mapper = new PizzaMapperImpl();

    @Test
    void modelToEntity() {
        var model = PizzaModel.builder()
                .id(1L)
                .name("Margherita")
                .ingredients("Ingredients")
                .build();

        var entity = mapper.modelToEntity(model);
        assertThat(entity).isNotNull()
                .returns(1L, Pizza::getId)
                .returns("Margherita", Pizza::getName)
                .returns("Ingredients", Pizza::getIngredients)
                .returns(null, Pizza::getDeleted);
    }

    @Test
    void entityToModel() {
        var entity = Pizza.builder()
                .id(1L)
                .name("Margherita")
                .ingredients("Ingredients")
                .build();
        var model = mapper.entityToModel(entity);
        assertThat(model).isNotNull()
                .returns(1L, PizzaModel::getId)
                .returns("Margherita", PizzaModel::getName)
                .returns("Ingredients", PizzaModel::getIngredients);
    }

    @Test
    void testNulls() {
        assertThat(mapper.entityToModel(null)).isNull();
        assertThat(mapper.modelToEntity(null)).isNull();
    }
}