package mvc.view.login

data class LoginUiState(
    var user: String = "",
    var password: String = "",
    var validUser: Boolean = false,
    var resultInfo: String = ""
)