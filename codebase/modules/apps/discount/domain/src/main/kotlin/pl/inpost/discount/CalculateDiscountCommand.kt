package pl.inpost.discount

data class CalculateDiscountCommand(val itemsQuantity: Int, val itemPrice: Money) {
    internal fun totalPrice() = itemPrice * itemsQuantity
}
