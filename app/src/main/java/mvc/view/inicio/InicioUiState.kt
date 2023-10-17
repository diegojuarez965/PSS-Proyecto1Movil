package mvc.view.inicio


data class InicioUiState(
    var nombre: String = "",
    var apellidos: String = "",
    var nro_afiliado: Int = 0,
    var nombre_plan: String = "",
    var sexo: String ="",
    var fechaNacimiento: String ="",
    var telefono: String="",
    var domicilio: String = "",
    var dni: Int = 0,
    var email: String = ""

)
