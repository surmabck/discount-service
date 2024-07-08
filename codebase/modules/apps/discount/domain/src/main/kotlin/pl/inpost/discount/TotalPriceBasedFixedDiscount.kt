package pl.inpost.discount

class TotalPriceBasedFixedDiscount(
    private val configuration: DiscountConfiguration
) : DiscountPolicy {

    override fun discountFor(command: CalculateDiscountCommand): Discount =
        configuration.findFor(command.totalPrice().toDouble())

}