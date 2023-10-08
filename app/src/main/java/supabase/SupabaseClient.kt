package supabase

import io.github.jan.supabase.*
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientInjector {

    fun makeClient(): SupabaseClient {
        val client = createSupabaseClient(
            supabaseUrl = "https://wzxxjubrcqoctwskoffi.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind6eHhqdWJyY3FvY3R3c2tvZmZpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTYzNzA4OTUsImV4cCI6MjAxMTk0Njg5NX0.CBAYKAOmS6ivwS5QQKNg6dkhlj4tZiTdrvApt8ihTO8"
        ) {
            install(Postgrest)
        }
        return client
    }
}