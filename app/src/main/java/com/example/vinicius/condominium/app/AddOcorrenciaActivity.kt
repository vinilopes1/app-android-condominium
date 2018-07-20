package com.example.vinicius.condominium.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Ocorrencia
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_add_entrada.*
import kotlinx.android.synthetic.main.activity_add_ocorrencia.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddOcorrenciaActivity : AppCompatActivity() {

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ocorrencia)
        initComponents()

    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(getToken())
    }

    private fun registrarOcorrencia(ocorrencia: Ocorrencia) {
        val call = apiService.ocorrenciaEndPoint.postOcorrencia(ocorrencia)

        call.enqueue(object: Callback<Ocorrencia>{
            override fun onFailure(call: Call<Ocorrencia>?, t: Throwable?) {
                Toast.makeText(this@AddOcorrenciaActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Ocorrencia>?, response: Response<Ocorrencia>?) {
                if (response!!.isSuccessful){
                    Toast.makeText(this@AddOcorrenciaActivity, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@AddOcorrenciaActivity, "Erro: " + response.code() + response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun criarOcorrencia(): Ocorrencia {
        var descricao = editDescricaoOcorrencia.text.toString()
        var localizacao = editLocalizacao.text.toString()
        var publico = true

        return Ocorrencia(descricao, localizacao, publico)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_entrada, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.item_confirmar){
            registrarOcorrencia(criarOcorrencia())
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }
}
