package com.univalle.picobotellamvvm.webservice
import com.univalle.picobotellamvvm.model.PokemonList
import com.univalle.picobotellamvvm.utils.Constants.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
interface ApiService {
    @GET(ENDPOINT)
    suspend fun getListPokemon(): Response<PokemonList>
}