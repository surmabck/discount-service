package pl.inpost.discount.port

import java.math.BigDecimal

fun interface ProductPriceProvider {
    fun productPrice(productId: String): BigDecimal?
}