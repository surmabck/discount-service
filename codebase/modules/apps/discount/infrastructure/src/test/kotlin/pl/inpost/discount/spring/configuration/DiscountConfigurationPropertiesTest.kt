package pl.inpost.discount.spring.configuration

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import pl.inpost.discount.DiscountConfiguration
import pl.inpost.discount.DiscountConfiguration.Entry
import pl.inpost.discount.PercentageDiscount
import pl.inpost.discount.all.rest.TestConfig

@WebMvcTest
@ContextConfiguration(
    classes = [TestConfig::class, DiscountModuleConfiguration::class]
)
internal class DiscountConfigurationPropertiesTest {

    @Test
    fun `should read QuantityBasedDiscountConfiguration properties from yaml`(
        @Autowired configuration: QuantityBasedDiscountConfiguration
    ) {
        configuration.asDiscountConfiguration() shouldBe DiscountConfiguration(
            listOf(
                Entry(5.0, PercentageDiscount(5)),
                Entry(10.0, PercentageDiscount(40))
            )
        )
    }

    @Test
    fun `should read TotalPriceBasedDiscountConfiguration properties from yaml`(
        @Autowired configuration: TotalPriceBasedDiscountConfiguration
    ) {
        configuration.asDiscountConfiguration() shouldBe DiscountConfiguration(
            listOf(
                Entry(100.5, PercentageDiscount(5.5)),
                Entry(200.0, PercentageDiscount(7))
            )
        )
    }
}