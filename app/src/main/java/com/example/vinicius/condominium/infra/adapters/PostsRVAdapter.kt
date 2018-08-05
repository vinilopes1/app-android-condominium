package com.example.vinicius.condominium.infra.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.models.Post

class PostsRVAdapter(
        var activity: Activity,
        var context: Context,
        var posts: MutableList<Post>
): RecyclerView.Adapter<PostsRVAdapter.ViewHolder>(){

    var cont = 0

    class ViewHolder(itemView: View?):RecyclerView.ViewHolder(itemView) {

        lateinit var txtNome: TextView
        lateinit var txtData: TextView
        lateinit var txtHora: TextView
        lateinit var txtTipoPost: TextView
        lateinit var txtDescricao: TextView
        lateinit var txtLocalizacao: TextView
        //lateinit var imgOcorrencia: ImageView


        init{


            txtNome = itemView!!.findViewById(R.id.txtNomeUsuario)
            txtData = itemView!!.findViewById(R.id.txtData)
            txtHora = itemView!!.findViewById(R.id.txtHora)
            txtTipoPost = itemView!!.findViewById(R.id.txtTipoPost)
            txtDescricao = itemView!!.findViewById(R.id.txtDescricao)
            txtLocalizacao = itemView!!.findViewById(R.id.txtLocalizacao)
            //imgOcorrencia = itemView!!.findViewById(R.id.imgOcorrencia)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var context = parent.context
        var inflater = LayoutInflater.from(context)
        lateinit var view: View

        if (posts.get(cont).tipo == "ocorrencia") {
            view = inflater.inflate(R.layout.item_timeline_ocorrencia, parent, false)
        } else view = inflater.inflate(R.layout.item_timeline_entrada, parent, false)
        cont++

        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = posts.get(position)

        if(post.tipo == "ocorrencia"){
            holder.txtNome.text = post.informante.nome
            holder.txtData.text = "23 de Junho"
            holder.txtHora.text = "15:30"
            holder.txtTipoPost.text = "Ocorrência"
            holder.txtDescricao.text = post.descricao
            holder.txtLocalizacao.text = "Quadra Poliesportiva"

        }
       else{
            holder.txtNome.text = post.informante.nome;
            holder.txtData.text = "23 de Junho"
            holder.txtHora.text = "15:31"
            holder.txtTipoPost.text = "Entrada Informada"
            holder.txtDescricao.text = post.descricao
//            holder.itemView.setBackgroundResource(R.drawable.ic_check_green_18dp)
//            holder.txtTipoPost.setTextColor(R.color.greenColor)

//            when(post.tipo){
//                "Entrada Informada" -> {
//                                        holder.itemView.setBackgroundResource(R.drawable.ic_check_green_18dp)
//                                        holder.txtTipoPost.setTextColor(R.color.greenColor)
//                                        }
//
//                "Entrada Expirada" ->{
//                                        holder.itemView.setBackgroundResource(R.drawable.ic_check_green_18dp)
//                                        holder.txtTipoPost.setTextColor(R.color.greenColor)
//                }
//                else -> null
//
//            }
        }

    }
}