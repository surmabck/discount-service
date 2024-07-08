package pl.inpost.product

import org.springframework.web.servlet.function.ServerResponse
import pl.inpost.product.generated.model.PriceWeb
import pl.inpost.product.generated.model.ProductWeb

internal class GetProductHandler(private val facade: ProductFacade) {

    fun getProduct(productId: String): ServerResponse =
        facade.getProductDetails(productId)
            ?.let { ServerResponse.ok().body(it.toWeb()) }
            ?: ServerResponse.notFound().build()

}


private fun Product.toWeb() = ProductWeb(
    productId = this.id.value,
    name = this.name,
    price = PriceWeb(
        value = this.price.value
    )
)