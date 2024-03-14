package data.models

data class Category(
    var id: Int?,
    var name: String?,
    var parentId: Int?,
    var inverseParent: List<Category> = listOf(),
    var parent: Category?,
    var products: List<Product> = listOf(),
):java.io.Serializable