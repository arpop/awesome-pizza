package awesomepizza.order.management.soa.repository;

import awesomepizza.order.management.soa.entity.Pizza;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
    List<Pizza> findByNameContainingAndDeletedFalse(String name);
    @Modifying
    @Query("update Pizza p set p.deleted = true where p.id = :id")
    void deletePizzaById(@Param("id") @NonNull Long id);

    @Query("select p from Pizza p where p.deleted = false")
    @NonNull
    List<Pizza> getAll();
}
