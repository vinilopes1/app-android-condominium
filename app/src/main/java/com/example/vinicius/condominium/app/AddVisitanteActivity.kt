package com.example.vinicius.condominium.app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Visitante
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.content_add_visitante.*
import kotlinx.android.synthetic.main.item_lista_visitantes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddVisitanteActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_visitante)
        initComponents()
    }

    private fun initComponents() {
        setupToolbar()
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(getToken())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_visitante, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.item_confirmar_visitante){
            registrarVisitante(criarVisitante())
        }

        return super.onOptionsItemSelected(item)
    }

    private fun registrarVisitante(visitante: Visitante) {
        val call = apiService.visitanteEndPoint.postVisitante(visitante)

        call.enqueue(object: Callback<Visitante>{
            override fun onResponse(call: Call<Visitante>?, response: Response<Visitante>?) {
                if (response!!.isSuccessful){
                    finish()
                    Toast.makeText(this@AddVisitanteActivity, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@AddVisitanteActivity, "Erro:" + response.errorBody()!!.string(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Visitante>?, t: Throwable?) {
                Toast.makeText(this@AddVisitanteActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun criarVisitante(): Visitante {
        val nome = editNomeVisitante.text.toString()
        lateinit var sexo: String
        val dataNascimento = editDataNascimentoVisitante.text.toString()
        val telefone = editTelefoneVisitante.text.toString()

        when(rdGrpSexoVisitante.checkedRadioButtonId){
            R.id.rdBtn1 -> sexo = "M"
            R.id.rdBtn2 -> sexo = "F"
            else -> "M"
        }

        return Visitante(1, nome, sexo, telefone, dataNascimento)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }

    private fun setupToolbar(){
        toolbar = findViewById(R.id.toolbarAddVisitante)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent,0)
            this@AddVisitanteActivity.overridePendingTransition(R.anim.righttoleft,R.anim.stable)
        }
    }

}
