package pl.inpost.discount.usecase

import pl.inpost.discount.CalculateDiscountCommand
import pl.inpost.discount.DiscountPolicy
import pl.inpost.discount.Money
import pl.inpost.discount.port.ProductPriceProvider

interface DiscountApplyingCalculator {
    fun calculate(itemId: String, quantity: Int): Money?
}
class PriceWithAllDiscountsCalculator(
    private val discountPolicies: List<DiscountPolicy>,
    private val productPriceProvider: ProductPriceProvider,
    private val maximalDiscount: Double //TODO add Percentage wrapper
) : DiscountApplyingCalculator {
    init {
        @Suppress("MagicNumber")
        require(maximalDiscount in 0.0..100.0) { "MaximalDiscount must be between 0 and 100 inclusive" }
    }

    override fun calculate(itemId: String, quantity: Int): Money? {
        val itemPrice = productPriceProvider.productPrice(itemId)?.let { Money(it.setScale(2)) } ?: return null
        val totalPrice = itemPrice * quantity
        val minimalPrice = totalPrice - (totalPrice.percentage(maximalDiscount))
        val totalDiscount = discountPolicies.map { it.discountFor(command(quantity, itemPrice)) }
            .fold(totalPrice) { price, discount -> price - discount.apply(totalPrice) }
        return maxOf(totalDiscount, minimalPrice)
    }
}

private fun command(quantity: Int, itemPrice: Money) =
    CalculateDiscountCommand(
        itemsQuantity = quantity,
        itemPrice = itemPrice
    )