package login.model.repository

import login.model.entities.User
import login.model.entities.User.EmptyUser

interface ClientRepository {

    suspend fun getClient(username: String, password: String): User

}

internal class ClientRepositoryImpl(
    private val clientStorage: ClientStorage
): ClientRepository {
    override suspend fun getClient(username: String, password: String): User {
        var client =
            try {
                clientStorage.getClient(username, password)
            }
            catch (e: Exception) {
                null
            }
        return client?: EmptyUser
    }
}