package data.models

data class UserAddress (
    var id: Int?,
    var userId: Int?,
    var address: String?,
    var orders: List<Order> = listOf(),
    var user: User?,
    var users: List<User> = listOf(),
):java.io.Serializable