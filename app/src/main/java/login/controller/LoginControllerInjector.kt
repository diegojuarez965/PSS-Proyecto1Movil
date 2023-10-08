package login.controller

import login.model.LoginModelImpl
import login.model.LoginModelInjector
import login.view.LoginView

object LoginControllerInjector {

    fun onViewStarted(loginView: LoginView) {
        LoginControllerImpl(LoginModelInjector.getLoginModel()).apply {
            setLoginView(loginView)
        }
    }
}