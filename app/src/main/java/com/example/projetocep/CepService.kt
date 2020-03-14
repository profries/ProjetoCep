package com.example.projetocep

import com.example.projetocep.model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService{
    @GET("{cep}/json")
    fun buscarCep(@Path("cep") cep:String): Call<Cep>
}