package pl.inpost.discount.spring.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.inpost.discount.DiscountPolicy
import pl.inpost.discount.port.ProductPriceProvider
import pl.inpost.discount.usecase.PriceWithAllDiscountsCalculator

@Configuration
internal class DiscountUseCasesConfiguration {

    @Bean
    fun totalPriceAfterDiscount(
        discountPolicies: List<DiscountPolicy>,
        productPriceProvider: ProductPriceProvider,
        @Value("\${discount.usecase.calculatePriceAfterDiscount.maximalDiscount}")
        maximalDiscount: Double
    ): PriceWithAllDiscountsCalculator = PriceWithAllDiscountsCalculator(
        discountPolicies = discountPolicies,
        productPriceProvider = productPriceProvider,
        maximalDiscount = maximalDiscount
    )
}