package com.example.vinicius.condominium.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity(): AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    lateinit private var securityPreferences: SecurityPreferences

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        initComponents()
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(this)
        initTab()
        initNavigation()

        fab_entrada.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddEntradaActivity::class.java)
            startActivity(intent)
        }

        fab_ocorrencia.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddOcorrenciaActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_entradas -> {

            }
            R.id.nav_ocorrencias -> {

            }
            R.id.nav_sobre -> {

            }
            R.id.nav_sair -> {
                logout()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {
        securityPreferences.limpar()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onClick(v: View?) {
    }

    fun initTab():Unit{
        val fragmentAdapter = TabsPager(supportFragmentManager)
        viewpager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(viewpager)
        setupTab()
    }

    fun setupTab(){
        //SetupTab01
        tab_layout.getTabAt(0)!!.setIcon(R.drawable.ic_feed_gray_24dp).setText(null)

        //SetupTab02
        tab_layout.getTabAt(1)!!.setIcon(R.drawable.ic_notifications_gray_24dp).setText(null)

        //SetupTab03
        tab_layout.getTabAt(2)!!.setIcon(R.drawable.ic_search_black_24dp).setText(null)
    }

    fun initNavigation(){
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_rodrigo)

        nav_view.setNavigationItemSelectedListener(this)
    }

}
//ADAPTER PRECISA PARA FUNCIONAR
private operator fun PagerAdapter?.invoke(tabsPager: TabsPager) {
}


