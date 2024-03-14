package data.models

data class Cart (
    var id: Int?,
    var userId: Int?,
    var productId: Int?,
    var amount: Int?,
    var orderId: Int?,
    var order: Order?,
    var product: Product?,
    var user: User?,
):java.io.Serializable