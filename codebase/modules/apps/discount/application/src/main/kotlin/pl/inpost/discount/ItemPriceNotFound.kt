package pl.inpost.discount

class ItemPriceNotFound(val itemId: String): IllegalStateException("Item $itemId price not found")