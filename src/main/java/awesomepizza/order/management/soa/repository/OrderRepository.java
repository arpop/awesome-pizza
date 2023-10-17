package awesomepizza.order.management.soa.repository;

import awesomepizza.order.management.soa.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("select o from Order o where o.orderStatus = 'PLACED' order by o.placementTime asc limit 1")
    Order getNextOrder();

    @Query("select o from Order o where o.orderStatus <> 'CANCELED' order by o.placementTime asc")
    List<Order> getAllOrders();
}
