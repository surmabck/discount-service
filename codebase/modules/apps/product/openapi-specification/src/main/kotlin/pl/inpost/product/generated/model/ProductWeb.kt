package pl.inpost.product.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * 
 * @param productId 
 * @param name 
 * @param price 
 */
data class ProductWeb(

    @get:Pattern(regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
    @get:Size(min=36,max=36)
    @Schema(example = "[\"123e4567-e89b-12d3-a456-426614174000\"]", required = true, description = "")
    @get:JsonProperty("productId", required = true) val productId: kotlin.String,

    @get:Size(min=3,max=64)
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @field:Valid
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("price", required = true) val price: PriceWeb
) {

}

