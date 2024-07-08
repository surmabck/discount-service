package pl.inpost.discount

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.inpost.discount.DiscountConfiguration.Entry

internal class QuantityBasedDiscountTest {

    @Test
    fun `should return zero discount when no stages is present`() {
        val quantityBasedDiscount = QuantityBasedDiscount(DiscountConfiguration(emptyList()))

        val discount = quantityBasedDiscount.discountFor(command(10))

        discount.amount shouldBe  0.0
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0",
        "1, 10.0",
        "19, 30.0",
        "20, 50.0",
        "100, 50.0"
    )
    fun `should apply largest applicable discount`(itemsQuantity: Int, expectedDiscount: Double) {
        val quantityBasedDiscount = QuantityBasedDiscount(
            DiscountConfiguration(
                listOf(
                    entry(min = 1, discount = 10.0),
                    entry(min = 5, discount = 30.0),
                    entry(min = 20, discount = 50.0),
                )
            )
        )

        val discount = quantityBasedDiscount.discountFor(command(itemsQuantity))

        discount.amount shouldBe expectedDiscount
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0",
        "1, 10.0",
        "19, 30.0",
        "20, 50.0",
        "100, 50.0"
    )
    fun `should apply largest applicable discount for unsorted stages`(itemsQuantity: Int, expectedDiscount: Double) {
        val quantityBasedDiscount = QuantityBasedDiscount(
            DiscountConfiguration(
                listOf(
                    entry(min = 5, discount = 30.0),
                    entry(min = 1, discount = 10.0),
                    entry(min = 20, discount = 50.0),
                )
            )
        )

        val discount = quantityBasedDiscount.discountFor(command(itemsQuantity))

        discount.amount shouldBe expectedDiscount
    }

    private fun entry(min: Int, discount: Double) = Entry(
        minimalValue = min.toDouble(),
        discount = PercentageDiscount(discount)
    )

    private fun command(itemsQuantity: Int) =
        CalculateDiscountCommand(itemsQuantity = itemsQuantity, itemPrice = Money.ZERO)

}