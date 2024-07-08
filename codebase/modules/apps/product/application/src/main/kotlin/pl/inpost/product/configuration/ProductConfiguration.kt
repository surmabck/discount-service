package pl.inpost.product.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.inpost.product.InMemoryProductDetailsProvider
import pl.inpost.product.Product
import pl.inpost.product.ProductDetailsProvider
import pl.inpost.product.ProductFacade
import pl.inpost.product.ProductId
import pl.inpost.product.ProductPrice

@Configuration
internal class ProductConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun productDetailsProvider(): ProductDetailsProvider = InMemoryProductDetailsProvider(
        listOf(
            Product(ProductId("45603f96-1ef0-465c-9a7f-bb9e106e2b64"), "Product 1", ProductPrice(10.toBigDecimal())),
            Product(ProductId("45603f96-1ef0-465c-9a7f-bb9e106e2b61"), "Product 2", ProductPrice(15.toBigDecimal()))
        )
    )

    @Bean
    internal fun productFacade(productDetailsProvider: ProductDetailsProvider) = ProductFacade
        .default(productDetailsProvider)

}