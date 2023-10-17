package awesomepizza.order.management.soa.repository;

import awesomepizza.order.management.soa.entity.PizzaOrder;
import com.sun.jdi.LongValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrderRepository extends CrudRepository<PizzaOrder, LongValue> {
}
