package com.example.vinicius.condominium.infra.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.models.Visitante
import kotlinx.android.synthetic.main.item_lista_visitantes.view.*

class VisitantesRVAdapater(
        var activity: Activity,
        var context: Context,
        var visitantes: MutableList<Visitante>
): RecyclerView.Adapter<VisitantesRVAdapater.ViewHolder>() {

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    override fun getItemCount(): Int {
        return visitantes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var contexto = parent.context
        var inflater = LayoutInflater.from(contexto)

        var view = inflater.inflate(R.layout.item_lista_visitantes, parent, false)
        var viewHolder = VisitantesRVAdapater.ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var visitante = visitantes.get(position)

        holder.itemView.txtNomeMoradorVisitante.text = "Registrado por " + visitante.morador.nome
        holder.itemView.txtNomeVisitante.text = visitante.nome

    }

}