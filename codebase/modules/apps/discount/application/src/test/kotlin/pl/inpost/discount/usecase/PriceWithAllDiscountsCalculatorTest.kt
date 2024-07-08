package pl.inpost.discount.usecase

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import pl.inpost.discount.DiscountPolicy
import pl.inpost.discount.Money
import pl.inpost.discount.PercentageDiscount

internal class PriceWithAllDiscountsCalculatorTest {

    @Test
    fun `should return null if no item returned from ProductPriceProvider`() {
        val useCase = PriceWithAllDiscountsCalculator(emptyList(), { null }, 0.0)

        val priceWithDiscount = useCase.calculate("", 1)

        priceWithDiscount shouldBe null
    }

    @Test
    fun `should grant no discount if there is no policy`() {
        val useCase = PriceWithAllDiscountsCalculator(
            discountPolicies = emptyList(),
            productPriceProvider = { 100.0.toBigDecimal() },
            maximalDiscount = 10.0
        )

        val priceWithDiscount = useCase.calculate("itemId", 1)

        priceWithDiscount!! shouldBe Money(100.0)
    }

    @Test
    fun `should grant no discount higher than maximalDiscount`() {
        val useCase = PriceWithAllDiscountsCalculator(
            discountPolicies = listOf(DiscountPolicy { PercentageDiscount(11.0) }),
            productPriceProvider = { 100.0.toBigDecimal() },
            maximalDiscount = 10.0
        )

        val priceWithDiscount = useCase.calculate("itemId", 1)

        priceWithDiscount!! shouldBe Money(90)
    }

    @Test
    fun `should apply discount if result is lower than maximalDiscount`() {
        val useCase = PriceWithAllDiscountsCalculator(
            discountPolicies = listOf(DiscountPolicy { PercentageDiscount(9.0) }),
            productPriceProvider = { 100.0.toBigDecimal() },
            maximalDiscount = 10.0
        )

        val priceWithDiscount = useCase.calculate("itemId", 1)

        priceWithDiscount!! shouldBe Money(91)
    }

    @Test
    fun `should apply all discounts`() {
        val useCase = PriceWithAllDiscountsCalculator(
            discountPolicies = listOf(
                DiscountPolicy { PercentageDiscount(9.0) },
                DiscountPolicy { PercentageDiscount(11.0) },
            ),
            productPriceProvider = { 100.0.toBigDecimal() },
            maximalDiscount = 100.0
        )

        val priceWithDiscount = useCase.calculate("itemId", 1)

        priceWithDiscount!! shouldBe Money(80)
    }

    @Test
    fun `should properly handle fractions `() {
        val useCase = PriceWithAllDiscountsCalculator(
            discountPolicies = listOf(
                DiscountPolicy { PercentageDiscount(9.0) },
                DiscountPolicy { PercentageDiscount(15.5) },
            ),
            productPriceProvider = { 100.0.toBigDecimal() },
            maximalDiscount = 100.0
        )

        val priceWithDiscount = useCase.calculate("itemId", 1)

        priceWithDiscount!! shouldBe Money(75.5)
    }
}