package login.view

data class LoginUiState(
    var user: String = "",
    var password: String = "",
    var validUser: Boolean = false,
    var error: String = ""
)