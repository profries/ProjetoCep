package com.example.projetocep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.projetocep.model.Cep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun carregarCep(view: View?){
        val textCep = findViewById<EditText>(R.id.textoCep)
        val textEstado = findViewById<TextView>(R.id.textEstado)
        val textCidade = findViewById<TextView>(R.id.textCidade)
        val textBairro = findViewById<TextView>(R.id.textBairro)
        val textLogradouro = findViewById<TextView>(R.id.textLogradouro)

        Log.i("Cep a buscar", textCep.text.toString())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CepService = retrofit.create(CepService::class.java)

        val cepCall = service.buscarCep(textCep.text.toString())

        cepCall.enqueue(object: Callback<Cep?> {
            override fun onFailure(call: Call<Cep?>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<Cep?>, response: Response<Cep?>) {
                if(response.isSuccessful){
                    response.body().let { cep: Cep? ->
                        Log.i("Estado", cep?.uf)
                        textEstado.setText("Estado:"+cep?.uf)
                        Log.i("Cidade", cep?.localidade)
                        textCidade.setText("Cidade:"+cep?.localidade)
                        Log.i("Bairro", cep?.bairro)
                        textBairro.setText("Bairro:"+cep?.bairro)
                        Log.i("Logradouro", cep?.logradouro)
                        textLogradouro.setText("Logradouro:"+cep?.logradouro)
                        Log.i("Complemento", cep?.complemento)
                    }
                }
                else{
                    Log.e("Erro",response.code().toString())
                }

            }
        })

    }
}
