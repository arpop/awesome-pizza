package awesomepizza.order.management.soa.controller;

import awesomepizza.order.management.soa.model.PizzaModel;
import awesomepizza.order.management.soa.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pizza")
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    @ResponseBody
    public List<PizzaModel> getAll() {
        return pizzaService.getAll();
    }

    @PostMapping
    @ResponseBody
    public PizzaModel addNewPizza(@RequestBody PizzaModel newPizza) {
        return pizzaService.save(newPizza);
    }

    @GetMapping("{id}")
    @ResponseBody
    public PizzaModel getById(@PathVariable("id") Long id) {
        return pizzaService.getById(id);
    }

    @GetMapping("search")
    @ResponseBody
    public List<PizzaModel> search(@RequestParam("name") String name) {
        return pizzaService.searchByName(name);
    }

    @DeleteMapping("{id}")
    public void deletePizza(@PathVariable("id") Long id) {
        pizzaService.delete(id);
    }

    @PutMapping
    public PizzaModel update(PizzaModel pizzaModel) {
        return pizzaService.update(pizzaModel);
    }
}
