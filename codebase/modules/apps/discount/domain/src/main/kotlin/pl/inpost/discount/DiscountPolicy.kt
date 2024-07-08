package pl.inpost.discount

fun interface DiscountPolicy {
    fun discountFor(command: CalculateDiscountCommand): Discount
}