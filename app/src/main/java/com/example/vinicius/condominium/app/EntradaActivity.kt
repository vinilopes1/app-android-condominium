package com.example.vinicius.condominium.app

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.vinicius.condominium.R
import android.content.Intent
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import com.example.vinicius.condominium.R.id.toolbar
import com.example.vinicius.condominium.R.id.txtNomeVisitanteEntradaDetalhe
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Detail
import com.example.vinicius.condominium.models.Entrada
import com.example.vinicius.condominium.models.Visitante
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_entrada.*
import kotlinx.android.synthetic.main.content_entrada.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EntradaActivity : AppCompatActivity() {

    lateinit var toolbar: android.support.v7.widget.Toolbar
    lateinit var progressDialog: ProgressDialog

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrada)
        initComponents()
    }

    private fun initComponents() {
        setupToolbar()
        progressDialog = initProgressDialog()
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(getToken())

        btnCancelarEntrada.setOnClickListener {
            cancelarEntrada(getEntradaSelecionada())
        }

        getEntradaDetalhada(getEntradaSelecionada())
    }

    private fun cancelarEntrada(entradaSelecionada: Int) {
        progressDialog.show()
        val call = apiService.entradaEndPoint.cancelarEntrada(entradaSelecionada)

        call.enqueue(object: Callback<Detail>{
            override fun onResponse(call: Call<Detail>?, response: Response<Detail>?) {
                if(response!!.isSuccessful){
                    finish()
                }else{
                    Toast.makeText(this@EntradaActivity, "Erro", Toast.LENGTH_SHORT).show()
                }
                progressDialog.hide()
            }

            override fun onFailure(call: Call<Detail>?, t: Throwable?) {
                Toast.makeText(this@EntradaActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }
        })
    }

    private fun getEntradaDetalhada(entradaSelecionada: Int) {
        progressDialog.show()
        val call = apiService.entradaEndPoint.getEntradaDetalhe(entradaSelecionada)

        call.enqueue(object: Callback<Entrada>{
            override fun onResponse(call: Call<Entrada>?, response: Response<Entrada>?) {
                if (response!!.isSuccessful){
                    exibirEntradaDetalhada(response.body()!!)
                }else{
                    Toast.makeText(this@EntradaActivity, response.errorBody()!!.string(), Toast.LENGTH_SHORT).show()
                }
                progressDialog.hide()
            }

            override fun onFailure(call: Call<Entrada>?, t: Throwable?) {
                Toast.makeText(this@EntradaActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }
        })
    }

    private fun exibirEntradaDetalhada(entrada: Entrada) {
        txtNomeUsuarioEntradaDetalhe.setText(entrada.informante.nome + " " + entrada.informante.sobrenome)
        txtNomeVisitanteEntradaDetalhe.setText(entrada.descricao)
        txtDataEntradaDetalhe.setText("Entrada em: " + entrada.dataEntrada)
        txtHoraEntradaDetalhe.setText(entrada.horaEntrada)

        if (entrada.informante.unidadeHabitacional != null){
            txtUnidadeEntradaDetalhe.setText(entrada.informante.unidadeHabitacional.grupoHabitacional.tipoGrupoHabitacional.toUpperCase()
                                    + " " + entrada.informante.unidadeHabitacional.grupoHabitacional.nome
                                + " - " + entrada.informante.unidadeHabitacional.grupoHabitacional.tipoUnidade
                                + " " + entrada.informante.unidadeHabitacional.nome)
        }
    }

    private fun getEntradaSelecionada(): Int {
        return intent.getIntExtra(CondomaisConstants.SELECTS.ENTRADA_SELECIONADA, 0)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }

    private fun initProgressDialog(): ProgressDialog {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Aguarde...")
        return progressDialog
    }

    private fun setupToolbar(){
        toolbar = findViewById(R.id.toolbarEntrada)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent,0)
            this@EntradaActivity.overridePendingTransition(R.anim.righttoleft,R.anim.stable)
        }
    }

}