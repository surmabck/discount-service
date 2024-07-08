package pl.inpost.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.inpost.discount.port.ProductPriceProvider
import pl.inpost.product.ProductFacade

@Configuration
class ModulesBinding {

    @Bean
    fun discountProductPriceProvider(productFacade: ProductFacade): ProductPriceProvider =
        ProductPriceProvider { productId -> productFacade.getProductDetails(productId)?.price?.value }

}