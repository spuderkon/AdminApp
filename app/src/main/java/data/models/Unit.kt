package data.models

data class Unit (
    var id: Int?,
    var measure: Int?,
    var name: String?,
    var products: List<Product>? = listOf(),
):java.io.Serializable