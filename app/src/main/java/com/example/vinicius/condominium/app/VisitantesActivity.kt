package com.example.vinicius.condominium.app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.adapters.VisitantesRVAdapater
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Visitante
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_visitantes.*
import kotlinx.android.synthetic.main.content_visitante.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VisitantesActivity : AppCompatActivity() {

    lateinit var toolbar: android.support.v7.widget.Toolbar

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitantes)
        initComponents()
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO))
        setupToolbar()

        getVisitantes()
    }

    private fun setupToolbar(){
        toolbar = findViewById(R.id.toolbarEntrada)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent,0)
            this@VisitantesActivity.overridePendingTransition(R.anim.righttoleft,R.anim.stable)
        }
    }

    private fun getVisitantes() {
        val call = apiService.visitanteEndPoint.getVisitantes()

        call.enqueue(object: Callback<MutableList<Visitante>>{
            override fun onResponse(call: Call<MutableList<Visitante>>?, response: Response<MutableList<Visitante>>?) {
                if(response!!.isSuccessful){
                    exibirLista(response.body()!!)
                }else{
                    Toast.makeText(this@VisitantesActivity, "Erro code " + response.code(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Visitante>>?, t: Throwable?) {
                Toast.makeText(this@VisitantesActivity, "Não foi possível estabelecer uma conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun exibirLista(listaVisitantes: MutableList<Visitante>) {
        val visitantesRVAdapater = VisitantesRVAdapater(this, this, listaVisitantes)

        rvVisitantes.adapter = visitantesRVAdapater

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rvVisitantes.layoutManager = linearLayoutManager
        rvVisitantes.setHasFixedSize(true)

    }


}
