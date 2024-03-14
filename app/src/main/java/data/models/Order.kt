package data.models

data class Order (
    var id: Int?,
    var userId: Int?,
    var orderDate: String?,
    var statusId: Int?,
    var totalPrice: Double?,
    var paymentMethodId: Int?,
    var paid: Boolean?,
    var addressId: Int?,
    var address: UserAddress?,
    var carts: List<Cart> = listOf(),
    var deliveries: List<Delivery> = listOf(),
    var paymentMethod: PaymentMethod?,
    var status: OrderStatus?,
):java.io.Serializable