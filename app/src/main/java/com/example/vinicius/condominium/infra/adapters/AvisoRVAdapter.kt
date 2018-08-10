package com.example.vinicius.condominium.infra.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.models.Aviso
import java.nio.file.Files.size

class AvisoRVAdapter(
        var activity: Activity,
        var context: Context,
        var avisos: MutableList<Aviso>
): RecyclerView.Adapter<AvisoRVAdapter.ViewHolder>() {


    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {

        lateinit var txtDescricao: TextView
        lateinit var txtInformante: TextView

        init {
            txtDescricao = itemView!!.findViewById(R.id.txtDescricaoAviso)
            txtInformante = itemView!!.findViewById(R.id.txtNomeInformante)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var contexto = parent.context
        var inflater = LayoutInflater.from(contexto)

        var view = inflater.inflate(R.layout.item_lista_avisos, parent, false)
        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return avisos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var aviso = avisos.get(position)
//        var subtext = ""
//
//        while (i in 0..105)
//            subtext =  subtext+ aviso.descricao[i]
//        if(subtext.length > 105)
//            subtext = subtext+" ..."
        holder.txtDescricao.text = aviso.descricao
        holder.txtInformante.text = aviso.informante.nome

    }

}