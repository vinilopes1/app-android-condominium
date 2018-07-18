package com.example.vinicius.condominium.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.adapters.PostsRVAdapter
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Post
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment(){

    private lateinit var mView: View
    private lateinit var apiService: APIService
    private lateinit var securityPreferences: SecurityPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.home_fragment,container,false)
        initComponents()

        return mView
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this!!.context!!)
        apiService = APIService(getToken())

        getPosts()
    }

    private fun getPosts() {
        val call = apiService.postEndPoint.getPosts()

        call.enqueue(object: Callback<MutableList<Post>>{
            override fun onResponse(call: Call<MutableList<Post>>?, response: Response<MutableList<Post>>?) {
                if (response!!.isSuccessful){
                    exibirLista(response.body())
                }else{
                    Toast.makeText(context, " erro. " + response.code() + " " + response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Post>>?, t: Throwable?) {
                Toast.makeText(context, "Falha na conexão!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun exibirLista(posts: MutableList<Post>?) {
        val postsAdapter = PostsRVAdapter(this!!.activity!!, this!!.context!!, posts!!)

        rvTimeline.adapter = postsAdapter

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvTimeline.layoutManager = linearLayoutManager
        rvTimeline.setHasFixedSize(true)
    }

    private fun getToken(): String{
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }

}