package by.evisun.goldenlime.product

data class ProductDto(
    @JvmField val category: String,
    @JvmField val title: String,
    @JvmField val description: String,
    @JvmField val image: String?,
    @JvmField val price: Int,
    @JvmField val capacity: Int,
    @JvmField val isFavourite: Boolean,
) {
    constructor() : this("", "", "", null, 0, 0, false)
}