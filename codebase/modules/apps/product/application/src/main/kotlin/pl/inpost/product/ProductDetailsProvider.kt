package pl.inpost.product

interface ProductDetailsProvider {
    fun productDetails(productId: ProductId): Product?
}

internal class InMemoryProductDetailsProvider(
    initialProducts: List<Product>
) : ProductDetailsProvider {
    private val inMemoryProducts: Map<ProductId, Product> = initialProducts.associateBy(Product::id)

    override fun productDetails(productId: ProductId): Product? {
        return inMemoryProducts[productId]
    }
}