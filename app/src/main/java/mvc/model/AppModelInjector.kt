package mvc.model

import io.github.jan.supabase.SupabaseClient
import mvc.model.repository.ClientRepository
import mvc.model.repository.ClientRepositoryImpl
import mvc.model.repository.ClientStorage
import mvc.model.repository.ClientStorageImpl
import mvc.view.login.LoginView
import supabase.SupabaseClientInjector

object AppModelInjector {
    private lateinit var appModel: AppModel
    private val supabaseClient: SupabaseClient = SupabaseClientInjector.makeClient()

    fun getAppModel(): AppModel {
        return appModel
    }

    fun initLoginModel(loginView: LoginView) {
        val clientStorage: ClientStorage = ClientStorageImpl(supabaseClient)
        val clientRepository: ClientRepository = ClientRepositoryImpl(clientStorage)

        appModel = AppModelImpl(clientRepository)
    }
}