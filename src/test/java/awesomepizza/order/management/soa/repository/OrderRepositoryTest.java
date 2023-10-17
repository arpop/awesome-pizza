package awesomepizza.order.management.soa.repository;

import awesomepizza.order.management.soa.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/clear_all.sql", "/orders_repository_test.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/clear_all.sql"})
class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;

    @Test
    void getNextOrder() {
        assertThat(repository.getNextOrder())
                .isNotNull().returns(2L, Order::getId);
    }

    @Test
    void getAllOrders() {
        assertThat(repository.getAllOrders()).hasSize(2);
        assertThat(repository.findAll()).hasSize(3);
    }
}