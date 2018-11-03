package com.example.vinicius.condominium.app

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.adapters.ComentariosRVAdapter
import com.example.vinicius.condominium.infra.adapters.VisitantesRVAdapater
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Comentario
import com.example.vinicius.condominium.models.Ocorrencia
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ocorrencia.*
import kotlinx.android.synthetic.main.content_ocorrencia.*
import kotlinx.android.synthetic.main.content_visitante.*
import kotlinx.android.synthetic.main.item_timeline_ocorrencia.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OcorrenciaActivity : AppCompatActivity() {

    lateinit var toolbar: android.support.v7.widget.Toolbar
    lateinit var progressDialog: ProgressDialog

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocorrencia)
        initComponents()

    }

    private fun initComponents() {
        setupToolbar()
        progressDialog = initProgressDialog()
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(getToken())

        getOcorrenciaDetalhe(getOcorrenciaSelecionada())

        btnAddComentarioOcorrencia.setOnClickListener {
            registrarComentario(criarComentario())
        }
    }

    private fun registrarComentario(comentario: Comentario) {
        progressDialog.show()
        val call = apiService.ocorrenciaEndPoint.postComentario(getOcorrenciaSelecionada(), comentario)

        call.enqueue(object: Callback<Comentario>{
            override fun onFailure(call: Call<Comentario>?, t: Throwable?) {
                Toast.makeText(this@OcorrenciaActivity, "Falha na conexão!", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }

            override fun onResponse(call: Call<Comentario>?, response: Response<Comentario>?) {
                if (response!!.isSuccessful){
                    Toast.makeText(this@OcorrenciaActivity, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show()
                    editComentarioOcorrenciaDetalhe.clearComposingText()
                    this@OcorrenciaActivity.onRestart()
                }else{
                    Toast.makeText(this@OcorrenciaActivity, response.errorBody()!!.string(), Toast.LENGTH_SHORT).show()
                }
                progressDialog.hide()
            }
        })
    }

    private fun criarComentario(): Comentario {
        var descricao = editComentarioOcorrenciaDetalhe.text.toString()
        return Comentario(0, descricao)
    }

    private fun getOcorrenciaDetalhe(ocorrenciaSelecionada: Int) {
        progressDialog.show()
        val call = apiService.ocorrenciaEndPoint.getOcorrencia(ocorrenciaSelecionada)

        call.enqueue(object: Callback<Ocorrencia>{
            override fun onResponse(call: Call<Ocorrencia>?, response: Response<Ocorrencia>?) {
                if (response!!.isSuccessful){
                    exibirOcorrenciaDetalhe(response.body()!!)
                }else{
                    Toast.makeText(this@OcorrenciaActivity, response.errorBody()!!.string(), Toast.LENGTH_SHORT).show()
                }
                progressDialog.hide()
            }

            override fun onFailure(call: Call<Ocorrencia>?, t: Throwable?) {
                Toast.makeText(this@OcorrenciaActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }
        })
    }

    private fun exibirOcorrenciaDetalhe(ocorrencia: Ocorrencia) {
        txtNomeUsuarioOcorrenciaDetalhe.setText(ocorrencia.informante.nome + " " + ocorrencia.informante.sobrenome)
        txtStatusOcorrenciaDetalhe.setText("Ocorrência " + ocorrencia.status)
        txtDescricaoOcorrenciaDetalhe.setText(ocorrencia.descricao)
        Picasso.get()
                .load("algo")
                .error(R.drawable.no_image)
                .into(ivImagemOcorrenciaDetalhe)

        setComentarios(ocorrencia.comentarios)
    }

    private fun setComentarios(comentarios: MutableList<Comentario>) {
        val comentariosRVAdapter = ComentariosRVAdapter(this, this, comentarios)

        rvComentariosOcorrencia.adapter = comentariosRVAdapter

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rvComentariosOcorrencia.layoutManager = linearLayoutManager
        rvComentariosOcorrencia.setHasFixedSize(true)
    }

    private fun getOcorrenciaSelecionada(): Int {
        return intent.getIntExtra(CondomaisConstants.SELECTS.OCORRENCIA_SELECIONADA, 0)
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
        toolbar = findViewById(R.id.toolbarOcorrencia)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent,0)
            this@OcorrenciaActivity.overridePendingTransition(R.anim.righttoleft,R.anim.stable)
        }
    }
}
