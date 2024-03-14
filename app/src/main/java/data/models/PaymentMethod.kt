package data.models

data class PaymentMethod (
    var id: Int?,
    var name: String?,
    var orders: List<Order> = listOf(),
):java.io.Serializable