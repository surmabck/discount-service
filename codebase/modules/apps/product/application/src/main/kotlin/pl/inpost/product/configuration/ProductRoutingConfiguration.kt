package pl.inpost.product.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions
import org.springframework.web.servlet.function.ServerResponse
import pl.inpost.product.GetProductHandler
import pl.inpost.product.ProductFacade
import pl.inpost.product.openapi.rest.InterfaceHandler
import pl.inpost.security.OpenPath

@Configuration
internal class ProductRoutingConfiguration {
    companion object {
        const val PRODUCT_ID_PARAM = "productId"
    }

    @Bean
    fun getProductHandler(productFacade: ProductFacade) = GetProductHandler(productFacade)

    @Bean
    fun productInterfaceHandler(): InterfaceHandler = InterfaceHandler()

    @Bean
    internal fun productListing(
        getProductHandler: GetProductHandler,
        productInterfaceHandler: InterfaceHandler
    ): RouterFunction<ServerResponse> {
        return RouterFunctions.route()
            .GET("/products/_interface", productInterfaceHandler::openapi)
            .GET("/products/{$PRODUCT_ID_PARAM}") { req ->
                getProductHandler.getProduct(req.pathVariable(PRODUCT_ID_PARAM))
            }
            .build()
    }

    @Bean
    fun productOpenApiOpenUrl(): OpenPath = OpenPath("/products/_interface")
}