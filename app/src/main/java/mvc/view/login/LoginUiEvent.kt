package mvc.view.login

sealed class LoginUiEvent {
    object SingIn: LoginUiEvent()

    object recoverPassword: LoginUiEvent()
}