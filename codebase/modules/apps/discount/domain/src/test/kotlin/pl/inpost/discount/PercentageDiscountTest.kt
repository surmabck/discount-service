package pl.inpost.discount

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import pl.inpost.discount.Money.Companion.ZERO

class PercentageDiscountTest {

    @Test
    fun `should not apply any discount if amount is zero`() {
        val discount = PercentageDiscount(0.0)
        val price = Money(10)

        val priceAfterDiscount = discount.apply(price)

        priceAfterDiscount shouldBe ZERO
    }

    @Test
    fun `should not apply any discount if price is zero `() {
        val discount = PercentageDiscount(10.0)
        val price = Money.ZERO

        val priceAfterDiscount = discount.apply(price)

        priceAfterDiscount shouldBe ZERO
    }

    @Test
    fun `should apply discount`() {
        val discount = PercentageDiscount(15.00)
        val price = Money(10.0)

        val priceAfterDiscount = discount.apply(price)

        priceAfterDiscount shouldBe Money(1.5)
    }
}