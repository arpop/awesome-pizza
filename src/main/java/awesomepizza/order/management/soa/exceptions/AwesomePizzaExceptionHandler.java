package awesomepizza.order.management.soa.exceptions;

import awesomepizza.order.management.soa.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AwesomePizzaExceptionHandler {

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<String> duplicateEntityException(DataIntegrityViolationException exception) {
        return new ResponseEntity<>("Already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderSequenceViolationException.class)
    public ResponseEntity<String> orderSequenceViolationException() {
        return new ResponseEntity<>("This order can't be picked yet", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OrderAlreadyPlacedException.class)
    public ResponseEntity<String> orderAlreadyPlacedException() {
        return new ResponseEntity<>("Order already placed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OrderNotCompliantException.class)
    public ResponseEntity<String> orderNotCompliantException() {
        return new ResponseEntity<>("Order not acceptable", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PizzaAlreadyExistsException.class)
    public ResponseEntity<String> pizzaAlreadyExistsException() {
        return new ResponseEntity<>("Pizza already exists", HttpStatus.BAD_REQUEST);
    }
}
