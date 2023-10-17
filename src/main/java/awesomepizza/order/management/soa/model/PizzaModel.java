package awesomepizza.order.management.soa.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PizzaModel {
    private Long id;
    private String name;
    private String ingredients;
}
