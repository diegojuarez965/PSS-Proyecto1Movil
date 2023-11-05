package mvc.controller

import mvc.model.AppModelInjector
import mvc.view.home.InicioView
import mvc.view.login.LoginView

object AppControllerInjector {

    private lateinit var appController: AppController
    fun onLoginViewStarted(loginView: LoginView) {
        appController = AppControllerImpl(AppModelInjector.getAppModel()).apply {
            setLoginView(loginView)
        }
    }
}