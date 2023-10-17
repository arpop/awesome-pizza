package awesomepizza.order.management.soa.controller;

import awesomepizza.order.management.soa.model.PizzaModel;
import awesomepizza.order.management.soa.service.PizzaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PizzaControllerTest {

    @Mock
    private PizzaService pizzaService;

    @InjectMocks
    private PizzaController controller;

    @Test
    void testGetAll() {
        controller.getAll();
        verify(pizzaService, times(1)).getAll();
    }

    @Test
    void testAddNewPizza() {
        var pizza = PizzaModel.builder().build();
        controller.addNewPizza(pizza);
        verify(pizzaService, times(1)).save(pizza);
    }

    @Test
    void testGetById() {
        controller.getById(7L);
        verify(pizzaService, times(1)).getById(7L);
    }

    @Test
    void testDelete() {
        controller.deletePizza(7L);
        verify(pizzaService, times(1)).delete(7L);
    }

    @Test
    void testSearchByName() {
        controller.search("name");
        verify(pizzaService, times(1)).searchByName("name");
    }

    @Test
    void testUpdate() {
        var pizza = PizzaModel.builder().build();
        controller.update(pizza);
        verify(pizzaService, times(1)).update(pizza);
    }
}