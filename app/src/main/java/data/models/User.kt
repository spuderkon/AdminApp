package data.models

data class User (
    var id: Int?,
    var name: String?,
    var surname: String?,
    var lastname: String?,
    var email: String?,
    var phoneNumber: String?,
    var roleId: Int?,
    var password: String?,
    var passwordSalt: String?,
    var userAddressId: Int?,
    var address: UserAddress?,
    var carts: List<Cart> = listOf(),
    var deliveries: List<Delivery> = listOf(),
    var role: Role?,
    var userAddresses: List<UserAddress> = listOf(),
):java.io.Serializable{

    fun getFullName(): String{
        return "$surname $name $lastname"
    }

    fun getShortName(): String{
        return "$surname $name"
    }
}