package mvc.model.repository

import mvc.model.entities.Cotitular
import mvc.model.entities.User
import mvc.model.entities.User.EmptyUser
import mvc.model.entities.User.ClientUser

interface ClientRepository {
    suspend fun authenticateUser(username: String, password: String): Boolean

    fun getClient(): User
    fun getCotitulars(): List<Cotitular>

}

internal class ClientRepositoryImpl(
    private val clientStorage: ClientStorage
): ClientRepository {
    private lateinit var currentClient: User
    private lateinit var currentCotitulars: List<Cotitular>

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

    override fun getCotitulars(): List<Cotitular> {
        return currentCotitulars
    }

    private suspend fun getClientFromDatabase(username: String, password: String) {

            try {
                currentClient = clientStorage.getClient(username, password)
                (currentClient as? ClientUser)?.let {
                    currentCotitulars = clientStorage.getCotitulars(it.nro_afiliado)
                }
            }
            catch (e: Exception) {
                currentClient = EmptyUser
            }
    }
}