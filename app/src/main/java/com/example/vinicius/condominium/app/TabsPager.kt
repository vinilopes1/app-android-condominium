package com.example.vinicius.condominium.app

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabsPager: FragmentStatePagerAdapter{

    var titles: Array<String> = arrayOf("Home","Avisos","Pesquisa")

    constructor(fm:FragmentManager): super(fm)

    override fun getPageTitle(position: Int): CharSequence? {

        when(position) {
            0 -> return "Home"
            1 -> return "Avisos"
            2 -> return "Pesquisa"
            else -> return "Message"

        }
    }

    override fun getItem(position: Int): Fragment? {

        var homeFragment = HomeFragment()
        var avisoFragment = AvisoFragment()
        var pesquisaFragment = PesquisaFragment()

        when(position){
            0 -> return homeFragment
            1 -> return avisoFragment
            2 -> return pesquisaFragment
            3 -> return homeFragment
        }

        return null
    }

    override fun getCount(): Int {
        return 4
    }


}