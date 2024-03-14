package data.models

data class Role (
    var id: Int?,
    var name: String?,
    var users: List<User> = listOf(),
):java.io.Serializable{

    override fun toString(): String {
        return "$name"
    }
}
