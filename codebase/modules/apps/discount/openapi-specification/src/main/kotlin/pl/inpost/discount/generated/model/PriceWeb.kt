package pl.inpost.discount.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin

/**
 * 
 * @param totalPrice 
 */
data class PriceWeb(

    @get:DecimalMin("0")
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("totalPrice", required = true) val totalPrice: java.math.BigDecimal
) {

}

