package login.model

import login.model.entities.User
import login.model.repository.ClientRepository
import observers.Observable
import observers.Subject

interface LoginModel {
    val userObservable: Observable<User>

    suspend fun getUser(username: String, password: String)
}

class LoginModelImpl(private val repository: ClientRepository): LoginModel {
    override val userObservable = Subject<User>()

    override suspend fun getUser(username: String, password: String) {
        repository.getClient(username, password).let {
            userObservable.notify(it)
        }
    }
}