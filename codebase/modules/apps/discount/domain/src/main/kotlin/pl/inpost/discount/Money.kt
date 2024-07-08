package pl.inpost.discount

import java.math.BigDecimal
import java.math.BigDecimal.valueOf

class Money : Comparable<Money> {
    companion object {
        val ZERO = Money(0)
        private val A_HOUNDRED_BD = BigDecimal("100.00")
        private const val SCALE = 2
    }

    private val value: BigDecimal

    constructor(value: BigDecimal) {
        require(value.scale() == SCALE) { "Money scale have to be $SCALE" }
        this.value = value
    }

    constructor(amount: Int) : this(amount.toBigDecimal().setScale(SCALE))
    constructor(amount: Double) : this(amount.toBigDecimal().setScale(SCALE))

    operator fun plus(other: Money): Money =Money(this.value + other.value)
    operator fun minus(other: Money): Money = Money(this.value - other.value)
    operator fun times(other: Money): Money = Money(this.value * other.value)
    operator fun times(other: Int): Money = Money((this.value * other.toBigDecimal()).setScale(SCALE))
    operator fun div(other: Money): Money = Money(this.value / other.value)

    fun percentage(percentage: Double): Money = Money((value * valueOf(percentage) / A_HOUNDRED_BD).setScale(SCALE))
    fun toDouble() = this.value.toDouble()

    override fun compareTo(other: Money): Int = this.value.compareTo(other.value)

    fun equals(other: Double): Boolean = this.value.compareTo(other.toBigDecimal()) == 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        return value.compareTo(other.value) == 0
    }

    override fun hashCode(): Int {
        return value.setScale(SCALE).hashCode()
    }

    override fun toString(): String {
        return "Money(value=$value)"
    }

    fun toBigDecimal(): BigDecimal {
        return this.value
    }

}