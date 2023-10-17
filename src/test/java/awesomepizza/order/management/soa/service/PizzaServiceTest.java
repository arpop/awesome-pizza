package awesomepizza.order.management.soa.service;

import awesomepizza.order.management.soa.entity.Pizza;
import awesomepizza.order.management.soa.exception.ResourceNotFoundException;
import awesomepizza.order.management.soa.exceptions.PizzaAlreadyExistsException;
import awesomepizza.order.management.soa.mapper.PizzaMapper;
import awesomepizza.order.management.soa.mapper.PizzaMapperImpl;
import awesomepizza.order.management.soa.model.PizzaModel;
import awesomepizza.order.management.soa.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTest {
    @Mock
    private PizzaRepository pizzaRepository;

    @Spy
    private PizzaMapper pizzaMapper = new PizzaMapperImpl();

    @InjectMocks
    private PizzaService service;


    @Test
    void testGetById() {
        var pizza = Pizza.builder()
                .name("pizza")
                .ingredients("ingredients")
                .id(1L)
                .build();
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        var pizzaModel = service.getById(1L);
        assertThat(pizzaModel).isNotNull();
        assertThat(pizzaModel.getName()).isEqualTo(pizza.getName());
    }

    @Test
    void testGetByIdException() {
        when(pizzaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        assertThatThrownBy(() -> service.getById(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testSave() {
        var pizza = PizzaModel.builder()
                .name("pizza")
                .ingredients("ingredients")
                .build();
        when(pizzaRepository.save(Mockito.any())).thenReturn(Pizza.builder().name("pizza").build());
        var savedPizza = service.save(pizza);
        assertThat(savedPizza).isNotNull();
        assertThat(savedPizza.getName()).isEqualTo(pizza.getName());
    }

    @Test
    void testSaveExceptions() {
        assertThatThrownBy(() -> service.save(PizzaModel.builder().id(1L).build()))
                .isInstanceOf(PizzaAlreadyExistsException.class);

        when(pizzaRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> service.save(PizzaModel.builder().build()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testGetAll() {
        service.getAll();
        verify(pizzaRepository, times(1)).getAll();
    }

    @Test
    void testSearchByName() {
        var name = "margherita";
        service.searchByName(name);
        verify(pizzaRepository, times(1)).findByNameContainingAndDeletedFalse(name);
    }

    @Test
    void testUpdate() {
        when(pizzaRepository.save(any())).thenReturn(Pizza.builder().id(1L).name("margherita").build());
        when(pizzaRepository.existsById(1L)).thenReturn(true);
        var savedPizza = service.update(PizzaModel.builder().id(1L).build());
        assertThat(savedPizza).isNotNull();
        assertThat(savedPizza.getId()).isEqualTo(1L);
    }

    @Test
    void testUpdateExceptions() {
        when(pizzaRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> service.update(PizzaModel.builder().id(1L).build()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testDelete() {
        service.delete(1L);
        verify(pizzaRepository, times(1)).deletePizzaById(1L);
    }
}