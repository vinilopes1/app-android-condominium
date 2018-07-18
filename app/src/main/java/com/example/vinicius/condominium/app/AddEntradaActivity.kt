package com.example.vinicius.condominium.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Entrada
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_add_entrada.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Time
import java.util.*

class AddEntradaActivity : AppCompatActivity() {

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entrada)
        initComponents()

    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this)
        Toast.makeText(this, "" + this.getToken(), Toast.LENGTH_SHORT).show()
        apiService = APIService(getToken())
    }

    private fun registrarEntrada(entrada: Entrada) {
        val call = apiService.entradaEndPoint.postEntrada(entrada)

        call.enqueue(object: Callback<Entrada>{
            override fun onResponse(call: Call<Entrada>?, response: Response<Entrada>?) {
                if (response!!.isSuccessful){
                    Toast.makeText(this@AddEntradaActivity, "Adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@AddEntradaActivity, "Erro " + response.code() + response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Entrada>?, t: Throwable?) {
                Toast.makeText(this@AddEntradaActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun criarEntrada(): Entrada {
        var descricao = editDescricaoEntrada.text.toString()
        var data = editDataEntrada.text.toString()

        return Entrada(descricao, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_entrada, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.item_confirmar){
            registrarEntrada(criarEntrada())
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }
}
