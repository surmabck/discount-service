package pl.inpost.product

class ProductFacade internal constructor(
    private val productDetailsProvider: ProductDetailsProvider
) {
    companion object {
        fun default(productDetailsProvider: ProductDetailsProvider) = ProductFacade(productDetailsProvider = productDetailsProvider)
    }

    fun getProductDetails(id: String): Product? {
        return productDetailsProvider.productDetails(ProductId(id))
    }
}