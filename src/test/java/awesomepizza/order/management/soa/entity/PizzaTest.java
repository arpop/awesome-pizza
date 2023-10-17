package awesomepizza.order.management.soa.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaTest {

    public static Stream<Arguments> deletedSource() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(false, false),
                Arguments.of(true, true)

        );
    }

    @ParameterizedTest
    @MethodSource("deletedSource")
    void preInsert(Boolean deleted, Boolean expected) {
        var pizza = Pizza.builder().deleted(deleted).build();
        pizza.preInsert();
        assertThat(pizza.getDeleted()).isEqualTo(expected);
    }


}