package login.controller

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import login.model.LoginModel
import login.view.LoginUiEvent
import login.view.LoginView
import observers.Observer
import kotlin.concurrent.thread

interface LoginController {
    fun setLoginView(loginView: LoginView)
}

internal class LoginControllerImpl(
    private val loginModel: LoginModel
): LoginController {

    private lateinit var loginView: LoginView

    override fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
        loginView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<LoginUiEvent> =
        Observer { value ->
            when (value) {
                LoginUiEvent.SingIn -> singIn()
                LoginUiEvent.recoverPassword -> recoverPassword()
            }
        }

    private fun singIn() {
        GlobalScope.launch {
            loginModel.getUser(loginView.uiState.user, loginView.uiState.password)
        }
    }

    private fun recoverPassword() {}
}