package mvc.view

import mvc.controller.AppControllerInjector
import mvc.model.AppModelInjector
import mvc.view.home.InicioView
import mvc.view.login.LoginView

object AppViewInjector {
    fun init(loginView: LoginView) {
        AppModelInjector.initLoginModel(loginView)
        AppControllerInjector.onLoginViewStarted(loginView)
    }

}