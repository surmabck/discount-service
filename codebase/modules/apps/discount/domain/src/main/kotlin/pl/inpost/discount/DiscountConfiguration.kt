package pl.inpost.discount

data class DiscountConfiguration(val configurationEntries: List<Entry>) {
    data class Entry(val minimalValue: Double, val discount: Discount) {
        init {
            require(minimalValue >= 0.0) { "Minimal value have to be >= 0 " }
        }
    }

    init {
        val unique = configurationEntries.distinctBy { it.minimalValue }
        require(configurationEntries.size == unique.size) {
            "Configurations have to have unique minimal value"
        }
    }

    private val configurations = configurationEntries
        .sortedByDescending { it.minimalValue } + Entry(0.0, ZeroDiscount)

    fun findFor(value: Double) = this
        .configurations
        .first { value >= it.minimalValue }
        .discount

    override fun toString(): String {
        return "DiscountConfiguration(configurations=$configurations)"
    }
}