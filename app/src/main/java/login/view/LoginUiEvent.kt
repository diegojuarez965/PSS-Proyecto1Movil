package login.view

sealed class LoginUiEvent {
    object SingIn: LoginUiEvent()

    object recoverPassword: LoginUiEvent()
}