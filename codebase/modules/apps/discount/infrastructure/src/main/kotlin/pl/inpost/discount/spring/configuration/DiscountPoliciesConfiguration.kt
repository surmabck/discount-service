package pl.inpost.discount.spring.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.inpost.discount.DiscountPolicy
import pl.inpost.discount.QuantityBasedDiscount
import pl.inpost.discount.TotalPriceBasedFixedDiscount

@Configuration
@EnableConfigurationProperties(QuantityBasedDiscountConfiguration::class, TotalPriceBasedDiscountConfiguration::class)
internal class DiscountPoliciesConfiguration {

    @ConditionalOnProperty(
        prefix = "discount.policy.quantity",
        name = ["enabled"],
        matchIfMissing = false,
        havingValue = "true"
    )
    @Bean
    fun quantityDiscountPolicy(configuration: QuantityBasedDiscountConfiguration): DiscountPolicy =
        QuantityBasedDiscount(configuration.asDiscountConfiguration())

    @ConditionalOnProperty(
        prefix = "discount.policy.total-price",
        name = ["enabled"],
        matchIfMissing = false,
        havingValue = "true"
    )
    @Bean
    fun totalPriceDiscountPolicy(configuration: TotalPriceBasedDiscountConfiguration): DiscountPolicy =
        TotalPriceBasedFixedDiscount(configuration.asDiscountConfiguration())

}