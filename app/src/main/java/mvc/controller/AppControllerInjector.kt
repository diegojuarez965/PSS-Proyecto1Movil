package mvc.controller

import mvc.model.AppModelInjector
import mvc.view.login.LoginView

object AppControllerInjector {

    fun onViewStarted(loginView: LoginView) {
        LoginControllerImpl(AppModelInjector.getLoginModel()).apply {
            setLoginView(loginView)
        }
    }
}