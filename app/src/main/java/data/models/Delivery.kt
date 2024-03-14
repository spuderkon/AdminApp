package data.models

data class Delivery (
    var id: Int?,
    var orderId: Int?,
    var courierId: Int?,
    var dateArrive: String?,
    var order: Order?,
    var user: User?,
):java.io.Serializable