package pl.inpost.discount.spring.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import pl.inpost.discount.DiscountConfiguration
import pl.inpost.discount.DiscountConfiguration.Entry
import pl.inpost.discount.PercentageDiscount

@ConfigurationProperties(prefix = "discount.policy.quantity.configuration")
internal class QuantityBasedDiscountConfiguration(private val entries: List<MinimumValueToPercentage>) {
    fun asDiscountConfiguration() = DiscountConfiguration(entries.map(MinimumValueToPercentage::asEntry))
}

@ConfigurationProperties(prefix = "discount.policy.total-price.configuration")
internal class TotalPriceBasedDiscountConfiguration(private val entries: List<MinimumValueToPercentage>) {
    fun asDiscountConfiguration() = DiscountConfiguration(entries.map(MinimumValueToPercentage::asEntry))
}

internal data class MinimumValueToPercentage(val minimal: Double, val percentageDiscount: Double) {
    fun asEntry() = Entry(minimal, PercentageDiscount(percentageDiscount))
}
