package login.view

import login.controller.LoginControllerInjector
import login.model.LoginModelInjector

object LoginViewInjector {
    fun init(loginView: LoginView) {
        LoginModelInjector.initLoginModel(loginView)
        LoginControllerInjector.onViewStarted(loginView)
    }
}