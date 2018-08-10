package com.example.vinicius.condominium.app

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.vinicius.condominium.R
import android.content.Intent
import android.view.View
import android.widget.Toolbar
import com.example.vinicius.condominium.R.id.toolbar
import kotlinx.android.synthetic.main.activity_entrada.*


class EntradaActivity : AppCompatActivity() {

    lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrada)
        setupToolbar()
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
