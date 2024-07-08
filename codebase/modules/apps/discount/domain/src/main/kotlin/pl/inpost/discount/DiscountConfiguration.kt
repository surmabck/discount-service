package pl.inpost.discount

class DiscountConfiguration(configurationEntries: List<Entry>) {
    data class Entry(val minimalValue: Double, val discount: Discount)

    init {
        val unique = configurationEntries.distinctBy { it.minimalValue }
        require(configurationEntries.size == unique.size) {
            "Configurations have to have unique minimal quantities"
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscountConfiguration

        return configurations == other.configurations
    }

    override fun hashCode(): Int {
        return configurations.hashCode()
    }


}