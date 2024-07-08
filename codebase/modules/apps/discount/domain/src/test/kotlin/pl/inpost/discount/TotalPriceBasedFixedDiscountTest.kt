package pl.inpost.discount

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.inpost.discount.DiscountConfiguration.Entry

internal class TotalPriceBasedFixedDiscountTest {

    @Test
    fun `should return zero discount when no stages is present`() {
        val totalPriceBasedFixedDiscount = TotalPriceBasedFixedDiscount(DiscountConfiguration(emptyList()))

        val discount = totalPriceBasedFixedDiscount.discountFor(command(10, 10.0))

        discount.amount shouldBe 0.0
    }

    @ParameterizedTest
    @CsvSource(
        "0, 10, 0",
        "1, 10, 0",
        "1, 50, 3.0",
        "19, 5, 3.0",
        "4, 12.5, 3.0",
        "20, 1, 1.0",
        "100, 3, 10.0"
    )
    fun `should apply largest applicable discount`(itemsQuantity: Int, itemPrice: Double, expectedDiscount: Double) {
        val totalPriceBasedFixedDiscount = TotalPriceBasedFixedDiscount(
            DiscountConfiguration(
                listOf(
                    entry(min = 20.0, discount = 1.0),
                    entry(min = 50.0, discount = 3.0),
                    entry(min = 100.0, discount = 10.0),
                )
            )
        )

        val discount = totalPriceBasedFixedDiscount.discountFor(command(itemsQuantity, itemPrice))

        discount.amount shouldBe expectedDiscount
    }

    @ParameterizedTest
    @CsvSource(
        "0, 10, 0",
        "1, 10, 0",
        "1, 50, 3.0",
        "19, 5, 3.0",
        "20, 1, 1.0",
        "100, 3, 10.0"
    )
    fun `should apply largest applicable discount for unsorted configs`(
        itemsQuantity: Int,
        itemPrice: Double,
        expectedDiscount: Double
    ) {
        val totalPriceBasedFixedDiscount = TotalPriceBasedFixedDiscount(
            DiscountConfiguration(
                listOf(
                    entry(min = 20.0, discount = 1.0),
                    entry(min = 50.0, discount = 3.0),
                    entry(min = 100.0, discount = 10.0),
                )
            )
        )

        val discount = totalPriceBasedFixedDiscount.discountFor(command(itemsQuantity, itemPrice))

        discount.amount shouldBe  expectedDiscount
    }

    private fun entry(min: Double, discount: Double) = Entry(
        minimalValue = min,
        discount = PercentageDiscount(discount)
    )

    private fun command(itemsQuantity: Int, itemPrice: Double) =
        CalculateDiscountCommand(itemsQuantity = itemsQuantity, itemPrice = Money(itemPrice))

}