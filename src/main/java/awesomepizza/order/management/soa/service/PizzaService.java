package awesomepizza.order.management.soa.service;

import awesomepizza.order.management.soa.exception.ResourceNotFoundException;
import awesomepizza.order.management.soa.exceptions.PizzaAlreadyExistsException;
import awesomepizza.order.management.soa.mapper.PizzaMapper;
import awesomepizza.order.management.soa.model.PizzaModel;
import awesomepizza.order.management.soa.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaMapper pizzaMapper;

    public PizzaModel getById(Long id) {
        var pizza = pizzaRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return pizzaMapper.entityToModel(pizza);
    }

    @SneakyThrows
    public PizzaModel save(PizzaModel pizzaModel) {
        if (pizzaModel.getId() != null) {
            throw new PizzaAlreadyExistsException();
        }
        var savedPizza = pizzaRepository.save(pizzaMapper.modelToEntity(pizzaModel));
        return pizzaMapper.entityToModel(savedPizza);
    }

    public void delete(Long id) {
        pizzaRepository.deletePizzaById(id);
    }

    public List<PizzaModel> getAll() {
        return pizzaRepository.getAll().stream().map(pizzaMapper::entityToModel).toList();
    }

    public List<PizzaModel> searchByName(String pizzaName) {
        return pizzaRepository.findByNameContainingAndDeletedFalse(pizzaName)
                .stream()
                .map(pizzaMapper::entityToModel)
                .collect(Collectors.toList());
    }

    public PizzaModel update(PizzaModel pizzaModel) {
        if (!pizzaRepository.existsById(pizzaModel.getId())) {
            throw new ResourceNotFoundException();
        }

        var saved = pizzaRepository.save(pizzaMapper.modelToEntity(pizzaModel));
        return pizzaMapper.entityToModel(saved);
    }
}
