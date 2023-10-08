package login.model.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date
sealed class User {
    @Serializable
    data class ClientUser(
        @SerialName("nro_afiliado")
        val nro_afiliado: Int,
        @SerialName("nombres")
        val nombres: String,
        @SerialName("apellidos")
        val apellidos: String,
        @SerialName("sexo")
        val sexo: String,
        @SerialName("fecha_nacimiento")
        val fecha_nacimiento: String,
        @SerialName("telefono")
        val telefono: String,
        @SerialName("domicilio")
        val domicilio: String,
        @SerialName("dni")
        val dni: Int,
        @SerialName("email")
        val email: String,
        @SerialName("contrasena")
        val contrasena: String,
        @SerialName("nombre_plan")
        val nombre_plan: String
    ): User()

    object EmptyUser: User()
}