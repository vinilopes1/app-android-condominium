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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import android.support.v7.widget.Toolbar
import android.util.Base64
import kotlinx.android.synthetic.main.content_add_ocorrencia.*
import okhttp3.MediaType
import java.io.File
import okhttp3.RequestBody
import okhttp3.MultipartBody


class AddOcorrenciaActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val PICK_IMAGE = 100

    lateinit var toolbar: Toolbar

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences
    lateinit private var imageString: String
    lateinit private var image: File

    lateinit private var body: MultipartBody.Part
    lateinit private var name: RequestBody

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
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (takePictureIntent.resolveActivity(packageManager) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        }

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            val extras = data.extras
//            val imageBitmap = extras.get("data") as Bitmap
//            val file = File("path")
//            val outputStream = BufferedOutputStream(FileOutputStream(file))
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
//            imgFotoOcorrencia.setImageBitmap(imageBitmap as Bitmap)
//        }

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            val selectedImage = data.getData()
            val filePathColumn = Array(1) { MediaStore.Images.Media.DATA }
            val cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val filePath = cursor.getString(columnIndex)
            cursor.close()

            val file = File(filePath)

            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            body = MultipartBody.Part.createFormData("upload", file.name, reqFile)
            name = RequestBody.create(MediaType.parse("text/plain"), "upload_test")
        }
    }

    private fun imageToString(imageBitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun registrarOcorrencia(ocorrencia: Ocorrencia, body: MultipartBody.Part, name: RequestBody) {
        val call = apiService.ocorrenciaEndPoint.postOcorrencia(body, ocorrencia)

        call.enqueue(object: Callback<Ocorrencia>{
            override fun onFailure(call: Call<Ocorrencia>?, t: Throwable?) {
                Toast.makeText(this@AddOcorrenciaActivity,
                        "Falha na conexão" + t!!.message + "\ne: " + t!!.cause.toString(),
                        Toast.LENGTH_SHORT).show()
                editLocalizacao.setText(t!!.message + "\n" + t!!.cause.toString())
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
        var publico = switchPublicoOcorrencia.isChecked

        return Ocorrencia(descricao, localizacao, publico)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_entrada, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.item_confirmar){
            registrarOcorrencia(criarOcorrencia(), body, name)
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
