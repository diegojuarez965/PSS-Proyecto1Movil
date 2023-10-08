package mvc.model.repository

import mvc.model.entities.User
import mvc.model.entities.User.EmptyUser
import mvc.model.entities.User.ClientUser

interface ClientRepository {
    suspend fun authenticateUser(username: String, password: String): Boolean

    fun getClient(): User

}

internal class ClientRepositoryImpl(
    private val clientStorage: ClientStorage
): ClientRepository {
    private lateinit var currentClient: User

    override suspend fun authenticateUser(username: String, password: String): Boolean {
        getClientFromDatabase(username, password)
        val loginResult = when(currentClient) {
            is ClientUser -> true
            EmptyUser -> false
        }
        return loginResult
    }

    override fun getClient(): User {
        return currentClient
    }

    private suspend fun getClientFromDatabase(username: String, password: String) {
        currentClient =
            try {
                clientStorage.getClient(username, password)
            }
            catch (e: Exception) {
                EmptyUser
            }
    }
}