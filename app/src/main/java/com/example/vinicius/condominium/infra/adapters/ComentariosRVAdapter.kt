package com.example.vinicius.condominium.infra.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.models.Comentario
import kotlinx.android.synthetic.main.item_lista_comentarios.view.*

class ComentariosRVAdapter (
        var activity: Activity,
        var context: Context,
        var comentarios: MutableList<Comentario>
): RecyclerView.Adapter<ComentariosRVAdapter.ViewHolder>(){

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var context = parent.context
        var inflater = LayoutInflater.from(context)
        lateinit var view: View

        view = inflater.inflate(R.layout.item_lista_comentarios, parent, false)

        var viewHolder = ComentariosRVAdapter.ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comentario = comentarios.get(position)

        holder.itemView.txtDescricaoComentario.setText(comentario.descricao)
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }

}