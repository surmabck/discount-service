package pl.inpost.discount


sealed interface Discount {
    val amount: Double
    fun apply(price: Money): Money
}

data object ZeroDiscount : Discount {
    override val amount: Double = 0.0
    override fun apply(price: Money): Money = Money(amount)
}

data class PercentageDiscount (override val amount: Double): Discount {
    constructor(amount: Int) : this(amount.toDouble())

    init {
        require(amount > 0) { "Discount amount must be positive" }
    }

    override fun apply(price: Money): Money =
        when {
            Money.ZERO.equals(amount) -> Money.ZERO
            Money.ZERO == price -> Money.ZERO
            else -> price.percentage(amount)
        }
}