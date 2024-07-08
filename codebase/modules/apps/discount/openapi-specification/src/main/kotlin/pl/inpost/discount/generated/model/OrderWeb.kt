package pl.inpost.discount.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min

/**
 * 
 * @param itemId 
 * @param quantity 
 */
data class OrderWeb(

    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("itemId", required = true) val itemId: kotlin.String,

    @get:Min(1)
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("quantity", required = true) val quantity: kotlin.Int
) {

}

