package data.models

data class Product (
    var id: Int?,
    var name: String?,
    var categoryId: Int?,
    var description: String?,
    var image: String?,
    var unitId: Int?,
    var price: Double,
    var carts: List<Cart> = listOf(),
    var category: Category?,
    var unit: Unit?,
):java.io.Serializable