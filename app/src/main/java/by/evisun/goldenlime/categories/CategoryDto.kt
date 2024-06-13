package by.evisun.goldenlime.categories

data class CategoryDto(
    @JvmField val title: String,
    @JvmField val parent: String?,
    @JvmField val image: String?,
    @JvmField val isLast: Boolean,
) {
    constructor() : this("", null, null, false)
}