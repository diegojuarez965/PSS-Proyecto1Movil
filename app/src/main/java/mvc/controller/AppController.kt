package mvc.controller

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mvc.model.AppModel
import mvc.view.login.LoginUiEvent
import mvc.view.login.LoginView
import observers.Observer

interface LoginController {
    fun setLoginView(loginView: LoginView)
    fun setHomeView(homeView: LoginView)
    fun setRecoveryView(recoveryView: LoginView)
}

internal class LoginControllerImpl(
    private val appModel: AppModel
): LoginController {

    private lateinit var loginView: LoginView
    private lateinit var homeView: LoginView
    private lateinit var recoveryView: LoginView

    override fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
        loginView.uiEventObservable.subscribe(observer)
    }

    override fun setHomeView(homeView: LoginView) {
        TODO("Not yet implemented")
    }

    override fun setRecoveryView(recoveryView: LoginView) {
        TODO("Not yet implemented")
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
            appModel.isRegisteredUser(loginView.uiState.user, loginView.uiState.password)
        }
        if(loginView.uiState.validUser) {
            loginView.navigateToInitialWindow(loginView.uiState.user)
        }

    }

    private fun recoverPassword() {}
}