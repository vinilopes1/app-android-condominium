package com.example.vinicius.condominium.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.adapters.AvisoRVAdapter
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Aviso
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.aviso_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvisoFragment: Fragment(){

    lateinit private var mView: View
    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.aviso_fragment,container,false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this!!.context!!)
        apiService = APIService(getToken())

        getAvisos()
    }

    private fun getAvisos() {

        val avisosCall = apiService.avisoEndPoint.getAvisos()

        avisosCall.enqueue(object: Callback<MutableList<Aviso>>{
            override fun onResponse(call: Call<MutableList<Aviso>>?, response: Response<MutableList<Aviso>>?) {
                if (response!!.isSuccessful){
                    exibirLista(response.body())
                }else{
                    Toast.makeText(context, "Erro" + response.code() + " : " + response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Aviso>>?, t: Throwable?) {
                Toast.makeText(context, "Falha na conexão!", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun exibirLista(avisosList: MutableList<Aviso>?) {
        val avisoRVAdapter = AvisoRVAdapter(this!!.activity!!, this!!.context!!, avisosList!!)

        rvAvisos.adapter = avisoRVAdapter

        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvAvisos.layoutManager = linearLayoutManager
        rvAvisos.setHasFixedSize(true)
    }

    private fun getToken(): String {
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }
}