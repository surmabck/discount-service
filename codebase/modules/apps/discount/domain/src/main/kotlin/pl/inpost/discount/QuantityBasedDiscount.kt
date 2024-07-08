package pl.inpost.discount

class QuantityBasedDiscount(private val configuration: DiscountConfiguration) : DiscountPolicy {

    override fun discountFor(command: CalculateDiscountCommand): Discount =
        configuration.findFor(command.itemsQuantity.toDouble())

}