package pl.inpost.discount


sealed interface Discount {
    val amount: Double
    fun apply(price: Money): Money
}

data object ZeroDiscount : Discount {
    override val amount: Double = 0.0
    override fun apply(price: Money): Money = Money(amount)
}

class PercentageDiscount (override val amount: Double): Discount {
    constructor(amount: Int) : this(amount.toDouble())

    override fun apply(price: Money): Money =
        when {
            Money.ZERO.equals(amount) -> Money.ZERO
            Money.ZERO == price -> Money.ZERO
            else -> price.percentage(amount)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PercentageDiscount

        return amount == other.amount
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }

    override fun toString(): String {
        return "PercentageDiscount(amount=$amount)"
    }


}