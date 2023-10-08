package login.model

import io.github.jan.supabase.SupabaseClient
import login.model.repository.ClientRepository
import login.model.repository.ClientRepositoryImpl
import login.model.repository.ClientStorage
import login.model.repository.ClientStorageImpl
import login.view.LoginView
import supabase.SupabaseClientInjector

object LoginModelInjector {
    private lateinit var loginModel: LoginModel
    private val supabaseClient: SupabaseClient = SupabaseClientInjector.makeClient()

    fun getLoginModel(): LoginModel {
        return loginModel
    }

    fun initLoginModel(loginView: LoginView) {
        val clientStorage: ClientStorage = ClientStorageImpl(supabaseClient)
        val clientRepository: ClientRepository = ClientRepositoryImpl(clientStorage)

        loginModel = LoginModelImpl(clientRepository)
    }
}