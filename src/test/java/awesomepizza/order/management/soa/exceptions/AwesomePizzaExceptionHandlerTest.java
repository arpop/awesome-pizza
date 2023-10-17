package awesomepizza.order.management.soa.exceptions;

import awesomepizza.order.management.soa.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AwesomePizzaExceptionHandlerTest {
    private AwesomePizzaExceptionHandler exceptionHandler = new AwesomePizzaExceptionHandler();

    @Test
    void duplicateEntityException() {
        var response = exceptionHandler.duplicateEntityException(new DuplicateKeyException(""));
        assertThat(response).isNotNull()
                .returns(HttpStatus.BAD_REQUEST, status -> response.getStatusCode());
    }

    @Test
    void resourceNotFoundException() {
        var response = exceptionHandler.resourceNotFoundException(new ResourceNotFoundException());
        assertThat(response).isNotNull()
                .returns(HttpStatus.NOT_FOUND, status -> response.getStatusCode());
    }

    @Test
    void orderSequenceViolationException() {
        var response = exceptionHandler.orderSequenceViolationException();
        assertThat(response).isNotNull()
                .returns(HttpStatus.BAD_REQUEST, status -> response.getStatusCode());
    }

    @Test
    void orderAlreadyPlacedException() {
        var response = exceptionHandler.orderAlreadyPlacedException();
        assertThat(response).isNotNull()
                .returns(HttpStatus.BAD_REQUEST, status -> response.getStatusCode());
    }

    @Test
    void orderNotCompliantException() {
        var response = exceptionHandler.orderNotCompliantException();
        assertThat(response).isNotNull()
                .returns(HttpStatus.BAD_REQUEST, status -> response.getStatusCode());
    }

    @Test
    void pizzaAlreadyExistsException() {
        var response = exceptionHandler.pizzaAlreadyExistsException();
        assertThat(response).isNotNull()
                .returns(HttpStatus.BAD_REQUEST, status -> response.getStatusCode());
    }
}