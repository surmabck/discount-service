package pl.inpost.product.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin

/**
 * 
 * @param &#x60;value&#x60; 
 */
data class PriceWeb(

    @get:DecimalMin("0")
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("value", required = true) val `value`: java.math.BigDecimal
) {

}

