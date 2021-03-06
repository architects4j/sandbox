package com.architects4j.workshop.microstream.helidon.product;

import com.architects4j.workshop.microstream.helidon.product.infra.FieldPropertyVisibilityStrategy;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbVisibility;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Schema(name = "Product", description = "The entity that represents Item in a product")
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class Product {

    @Schema(required = true, name = "name", description = "The item name", example = "water")
    @NotBlank
    @Size(min = 3, max = 100, message = "The name size should be between 3 and 100 chars")
    private final String name;

    @Schema(required = true, name = "description", description = "The description's Product", example = "water")
    @NotBlank
    @NotNull
    @Size(min = 5, message = "should have at least 5 chars")
    private final  String description;

    @Schema(required = true, name = "quantity", description = "The quantity's Product", example = "0")
    @NotNull
    @Min(1)
    private final  Integer quantity;

    @JsonbCreator
    public Product(@JsonbProperty("name") String name,
                   @JsonbProperty("description") String description,
                   @JsonbProperty("quantity") Integer quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
