package mvc.model.repository

import io.github.jan.supabase.*
import io.github.jan.supabase.postgrest.postgrest
import mvc.model.entities.User
import mvc.model.entities.User.ClientUser

interface ClientStorage {

    suspend fun getClient(username: String, password: String): User
}

class ClientStorageImpl(
    private val supabaseClient: SupabaseClient
) : ClientStorage{
    override suspend fun getClient(username: String, password: String): User {
        return supabaseClient.postgrest[CLIENTS_TABLE].select(){
            eq(EMAIL, username)
            eq(PASSWORD, password)
        }.decodeSingle<ClientUser>()
    }


}