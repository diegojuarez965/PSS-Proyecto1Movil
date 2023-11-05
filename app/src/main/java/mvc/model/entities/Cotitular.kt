package mvc.model.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cotitular(
    @SerialName("nro_afiliado")
    val nro_afiliado: Int,
    @SerialName("nombres")
    val nombres: String,
    @SerialName("apellido")
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
    @SerialName("nombre_plan")
    val nombre_plan: String,
    @SerialName("id_cotitular")
    val id_plan: Int,
    @SerialName("relacion_con_titular")
    val relacion: String
)
