package com.example.shipatonapp
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://ockouigtxczvzrdbjxkp.supabase.co",
    supabaseKey = "sb_publishable_eut82xz6it625iWwqSDx5Q_U_VlZnQH"
) {
    install(Postgrest)
    install(Auth)
    install(Functions)
}