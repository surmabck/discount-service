package pl.inpost.product

import java.math.BigDecimal

data class Product(
    val id: ProductId,
    val name: String,
    val price: ProductPrice
)

data class ProductPrice(val value: BigDecimal)
data class ProductId(val value: String) {
    init {
        require(value.matches(Regex("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))) { "Value should be in UUID format" }
    }
}