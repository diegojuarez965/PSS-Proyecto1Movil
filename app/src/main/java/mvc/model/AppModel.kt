package mvc.model

import mvc.model.entities.User
import mvc.model.repository.ClientRepository
import observers.Observable
import observers.Subject

interface AppModel {
    val loginObservable: Observable<Boolean>

    suspend fun isRegisteredUser(username: String, password: String)

    fun getClient():User
}

class AppModelImpl(private val repository: ClientRepository): AppModel {
    override val loginObservable = Subject<Boolean>()

    override suspend fun isRegisteredUser(username: String, password: String) {
        repository.authenticateUser(username, password).let {
            loginObservable.notify(it)
        }
    }

    override fun getClient() =
        repository.getClient()

}