package com.example.vinicius.condominium.infra.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.models.Post
import kotlinx.android.synthetic.main.item_lista_timeline.view.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class PostsRVAdapter(
    var activity: Activity,
    var context: Context,
    var posts: MutableList<Post>
    ): RecyclerView.Adapter<PostsRVAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var txtNomeUsuario: TextView
        var txtDataHora: TextView
        var txtDescricao: TextView

        init {
            txtNomeUsuario = itemView.findViewById(R.id.txtNomeUsuarioItemTimeline)
            txtDataHora = itemView.findViewById(R.id.txtDataHoraItemTimeline)
            txtDescricao = itemView.findViewById(R.id.txtDescricaoItemTimeline)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var contexto = parent.context
        var inflater = LayoutInflater.from(contexto)

        var view = inflater.inflate(R.layout.item_lista_timeline, parent, false)
        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = posts.get(position)

        holder.txtDescricao.text = post.descricao
        holder.txtDataHora.text = post.data.split("T")[0].replace("-", "/")
//        holder.txtNomeUsuario.text = post.informante.usuario.username
    }
}