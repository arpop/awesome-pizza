package awesomepizza.order.management.soa.repository;

import awesomepizza.order.management.soa.entity.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/clear_all.sql", "/pizza_repository_test.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/clear_all.sql"})
class PizzaRepositoryTest {

    @Autowired
    private PizzaRepository repository;

    @Test
    void testSave() {
        var pizza = Pizza.builder().name("Crudo").ingredients("Pomodoro, Mozzarella, Crudo").build();
        var savedMargherita = repository.save(pizza);
        assertThat(savedMargherita.getId()).isNotNull();
    }

    @Test
    void testGetAll() {
        assertThat(repository.getAll()).hasSize(2);
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    void testDeleteById() {
        var atomicRefId = new AtomicReference<Long>();
        repository.findAll().forEach(pizza -> {
            if (pizza.getName().equals("Margherita")) {
                pizza.setDeleted(true);
                atomicRefId.set(pizza.getId());
                repository.save(pizza);
            }
        });
        var deleted = repository.findById(atomicRefId.get());
        assertThat(deleted).isNotEmpty();
        assertThat(deleted.get()).returns(true, Pizza::getDeleted);
    }
}