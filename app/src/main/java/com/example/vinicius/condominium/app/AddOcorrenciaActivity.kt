package com.example.vinicius.condominium.app

import android.app.Activity
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
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.content.CursorLoader
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.util.*
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.content_add_ocorrencia.*


class AddOcorrenciaActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ocorrencia)
        initComponents()

    }

    private fun initComponents() {
        setupToolbar()
        securityPreferences = SecurityPreferences(this)
        apiService = APIService(getToken())

        btnAddFotoOcorrencia.setOnClickListener {
            iniciarIntentCamera()
        }
    }

    private fun iniciarIntentCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            val imageBitmap = extras.get("data") as Bitmap
            imgFotoOcorrencia.setImageBitmap(imageBitmap)
        }
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
                    Toast.makeText(this@AddOcorrenciaActivity, "Erro: " + response.code() + response.errorBody()!!.string(), Toast.LENGTH_SHORT).show()
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

    private fun setupToolbar(){
        toolbar = findViewById(R.id.toolbarAddOcorrencia)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent,0)
            this@AddOcorrenciaActivity.overridePendingTransition(R.anim.righttoleft,R.anim.stable)
        }
    }
}
