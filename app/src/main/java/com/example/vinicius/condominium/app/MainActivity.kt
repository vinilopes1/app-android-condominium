package com.example.vinicius.condominium.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.annotation.IntegerRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.infra.adapters.AvisoRVAdapter
import com.example.vinicius.condominium.infra.adapters.PostsRVAdapter2
import com.example.vinicius.condominium.infra.api.APIService
import com.example.vinicius.condominium.models.Aviso
import com.example.vinicius.condominium.models.Post
import com.example.vinicius.condominium.utils.CondomaisConstants
import com.example.vinicius.condominium.utils.SecurityPreferences


import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.ionicons_typeface_library.Ionicons
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit private var apiService: APIService
    lateinit private var securityPreferences: SecurityPreferences

    lateinit var toggleProf: ImageView
    lateinit var mSwipeRefresh: SwipeRefreshLayout
    lateinit var mBodyMsg: TextView
    lateinit var mBodyNotif: TextView
    lateinit var mScreenTitle: TextView
    lateinit var mSearchBar: LinearLayout
    lateinit var fab: com.github.clans.fab.FloatingActionMenu
    lateinit var rvPosts: RecyclerView
    lateinit var rvAvisos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initComponents()

        toggleProf = findViewById(R.id.togglePic)
        mSwipeRefresh = findViewById(R.id.swiperefresh)
        mBodyMsg = findViewById(R.id.body_msg)
        mScreenTitle = findViewById(R.id.screen_title)
        mSearchBar = findViewById(R.id.search_bar)
        rvPosts = findViewById(R.id.rvPosts)
        rvAvisos = findViewById(R.id.rvAvisos)

        // The tweet now floating button
        setupFab()
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        // Logic for the side navigation drawer

        // The Account Profiles
        val profile1 = ProfileDrawerItem().withName("Opa").withEmail("@sdabhi23").withIcon(resources.getDrawable(R.drawable.hipolito2))

        val headerResult = AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.header)
                .withAccountHeader(R.layout.custom_header)
                .addProfiles(
                        profile1
                )
                .build()

        val result = DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(

                        PrimaryDrawerItem().withIdentifier(2).withName("Agendamentos").withIcon(FontAwesome.Icon.faw_user_o).withSelectable(false),
                        PrimaryDrawerItem().withIdentifier(3).withName("Reservas").withIcon(FontAwesome.Icon.faw_list_alt).withSelectable(false),
                        PrimaryDrawerItem().withIdentifier(4).withName("Entregas e Encomendas").withIcon(FontAwesome.Icon.faw_bolt).withSelectable(false),
                        PrimaryDrawerItem().withIdentifier(5).withName("Visitantes").withIcon(FontAwesome.Icon.faw_clone).withSelectable(false),
                        DividerDrawerItem(),
                        PrimaryDrawerItem().withIdentifier(6).withName("Status e Privacidade").withSelectable(false),
                        PrimaryDrawerItem().withIdentifier(7).withName("Ajuda").withSelectable(false)

                ).withOnDrawerItemClickListener { view, position, drawerItem -> true }
                .addStickyDrawerItems(

                        SecondaryDrawerItem().withIdentifier(8).withName("Sair do aplicativo").withIcon(FontAwesome.Icon.faw_qrcode).withIconColorRes(R.color.colorAccent2)
                                .withTextColorRes(R.color.colorAccent2)

                ).withOnDrawerItemClickListener{view, position, drawerItem ->
                    var item = drawerItem.identifier.toInt()
                    if(drawerItem != null){
                        if(item == 8){
                            logout()
                        }
                    }
                    return@withOnDrawerItemClickListener false
                }
                .build()

        toggleProf.setOnClickListener { view ->

            if (result.isDrawerOpen) {
                result.closeDrawer()
            } else {
                result.openDrawer()
            }

        }

        mScreenTitle.text = "Home"
        fab.visibility = View.VISIBLE
        mSearchBar.visibility = View.GONE

        // Logic for the navigation tabs

        val home = tabs.newTab().setIcon(IconicsDrawable(this).icon(FontAwesome.Icon.faw_home).colorRes(R.color.colorAccent2))
        val notif = tabs.newTab().setIcon(IconicsDrawable(this).icon(FontAwesome.Icon.faw_bell_o).colorRes(R.color.draw_description))
        val search = tabs.newTab().setIcon(IconicsDrawable(this).icon(Ionicons.Icon.ion_ios_search).colorRes(R.color.draw_description))
        val msg = tabs.newTab().setIcon(IconicsDrawable(this).icon(FontAwesome.Icon.faw_envelope_o).colorRes(R.color.draw_description))

        tabs.addTab(home)
        tabs.addTab(notif)
        tabs.addTab(search)
        tabs.addTab(msg)


        // Tabs
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tabs.getSelectedTabPosition() == 0) {

                    mScreenTitle.text = "Home"
                    mScreenTitle.visibility = View.VISIBLE
                    fab.visibility = View.VISIBLE
                    mSearchBar.visibility = View.GONE
                    rvAvisos.visibility = View.INVISIBLE

                    mSwipeRefresh.visibility = View.VISIBLE
                    mBodyMsg.visibility = View.INVISIBLE

                    home.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_home).colorRes(R.color.colorAccent2))
                    notif.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_bell_o).colorRes(R.color.draw_description))
                    search.setIcon(IconicsDrawable(applicationContext).icon(Ionicons.Icon.ion_ios_search).colorRes(R.color.draw_description))
                    msg.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_envelope_o).colorRes(R.color.draw_description))

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(mSearchBar.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                } else if (tabs.getSelectedTabPosition() == 1) {

                    mSwipeRefresh.visibility = View.INVISIBLE
                    mBodyMsg.visibility = View.INVISIBLE

                    mScreenTitle.text = "Avisos"
                    mScreenTitle.visibility = View.VISIBLE
                    fab.visibility = View.GONE
                    mSearchBar.visibility = View.GONE
                    rvAvisos.visibility = View.VISIBLE

                    home.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_home).colorRes(R.color.draw_description))
                    notif.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_bell).colorRes(R.color.colorAccent2))
                    search.setIcon(IconicsDrawable(applicationContext).icon(Ionicons.Icon.ion_ios_search).colorRes(R.color.draw_description))
                    msg.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_envelope_o).colorRes(R.color.draw_description))

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(mSearchBar.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                } else if (tabs.getSelectedTabPosition() == 2) {

                    mSwipeRefresh.visibility = View.INVISIBLE
                    mBodyMsg.visibility = View.INVISIBLE

                    mScreenTitle.visibility = View.GONE
                    fab.visibility = View.GONE
                    mSearchBar.visibility = View.VISIBLE
                    rvAvisos.visibility = View.INVISIBLE

                    home.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_home).colorRes(R.color.draw_description))
                    notif.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_bell_o).colorRes(R.color.draw_description))
                    search.setIcon(IconicsDrawable(applicationContext).icon(Ionicons.Icon.ion_ios_search).colorRes(R.color.colorAccent2))
                    msg.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_envelope_o).colorRes(R.color.draw_description))

                } else if (tabs.getSelectedTabPosition() == 3) {

                    mSwipeRefresh.visibility = View.INVISIBLE
                    mBodyMsg.visibility = View.VISIBLE

                    mScreenTitle.text = "Messages"
                    mScreenTitle.visibility = View.VISIBLE
                    fab.visibility = View.GONE
                    mSearchBar.visibility = View.GONE
                    rvAvisos.visibility = View.INVISIBLE

                    home.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_home).colorRes(R.color.draw_description))
                    notif.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_bell_o).colorRes(R.color.draw_description))
                    search.setIcon(IconicsDrawable(applicationContext).icon(Ionicons.Icon.ion_ios_search).colorRes(R.color.draw_description))
                    msg.setIcon(IconicsDrawable(applicationContext).icon(FontAwesome.Icon.faw_envelope).colorRes(R.color.colorAccent2))

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(mSearchBar.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)


                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        // Logica do swipe refresh

        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent2)

        mSwipeRefresh.setOnRefreshListener { Handler().postDelayed({ mSwipeRefresh.isRefreshing = false }, 5000) }


    }

    fun exibirListaPost(posts: MutableList<Post>){
        val adapter = PostsRVAdapter2(this@MainActivity, this!!.applicationContext, posts!!)

        rvPosts.adapter = adapter

        var linearLayoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        linearLayoutManager.scrollToPosition(0)
        rvPosts.layoutManager = linearLayoutManager
        rvPosts.setHasFixedSize(true)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    }

    private fun getPosts() {
        val call = apiService.postEndPoint.getPosts()

        call.enqueue(object: Callback<MutableList<Post>> {
            override fun onResponse(call: Call<MutableList<Post>>?, response: Response<MutableList<Post>>?) {
                if (response!!.isSuccessful){
                    exibirListaPost(response.body()!!)
                }else{
                    Toast.makeText(this@MainActivity, " erro. " + response.code() + " " + response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Post>>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Falha na conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAvisos() {

        val avisosCall = apiService.avisoEndPoint.getAvisos()

        avisosCall.enqueue(object: Callback<MutableList<Aviso>>{
            override fun onResponse(call: Call<MutableList<Aviso>>?, response: Response<MutableList<Aviso>>?) {
                if (response!!.isSuccessful){
                    exibirListaAviso(response!!.body())
                }else{
                    Toast.makeText(this@MainActivity, "Erro" + response.message() + " : " + response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Aviso>>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Falha na conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun exibirListaAviso(avisosList: MutableList<Aviso>?) {
        val avisoRVAdapter = AvisoRVAdapter(this@MainActivity, this!!.applicationContext, avisosList!!)

        rvAvisos.adapter = avisoRVAdapter

        var linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        rvAvisos.layoutManager = linearLayoutManager
        rvAvisos.setHasFixedSize(true)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun initComponents() {
        securityPreferences = SecurityPreferences(applicationContext)
        apiService = APIService(getToken())

        getPosts()
        getAvisos()
    }


    private fun getToken(): String {
        return securityPreferences.getSavedString(CondomaisConstants.KEY.TOKEN_LOGADO)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase))
    }

    private fun setupFab(){

        fab = findViewById(R.id.fab1)
        rvPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility === View.VISIBLE) {
                    fab1.hideMenu(true)
                } else if (dy < 0 && fab1.visibility !== View.VISIBLE) {
                    fab1.showMenu(true)
                }
            }
        })
    }

    private fun logout() {
        securityPreferences.limpar()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }



}