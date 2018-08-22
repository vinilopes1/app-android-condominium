package com.example.vinicius.condominium.infra.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.app.EntradaActivity
import com.example.vinicius.condominium.models.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_ocorrencia.view.*
import kotlinx.android.synthetic.main.item_timeline_entrada.view.*
import kotlinx.android.synthetic.main.item_timeline_ocorrencia.view.*

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

        if (posts.get(cont).tipo == "ocorrencia")
            view = inflater.inflate(R.layout.item_timeline_ocorrencia, parent, false)
        else
            view = inflater.inflate(R.layout.item_timeline_entrada, parent, false)

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

        holder.itemView.setOnClickListener { view ->
            onClick(post, view)
        }

        holder.txtNome.text = post.informante.nome
        holder.txtData.text = post.data
        holder.txtHora.text = post.hora
        holder.txtTipoPost.text = post.status
        holder.txtDescricao.text = post.descricao

        if(post.tipo == "ocorrencia"){
            holder.txtLocalizacao.text = "Quadra Poliesportiva"
            Picasso.get()
                    .load(post.foto)
                    .into(holder.itemView.imgOcorrencia)
        }else{
            when(post.status){ //Informada, Lida, Atendida, Cancelada or Expirada
                "Entrada informada" -> {
                    holder.itemView.icStatus.setImageResource(R.drawable.ic_check_green_18dp)
                    holder.txtTipoPost.setTextColor(Color.parseColor("#23CC23"))
                }

                "Entrada cancelada" ->{
                    holder.itemView.icStatus.setImageResource(R.drawable.ic_cancel_black_18dp)
                    holder.txtTipoPost.setTextColor(activity.resources.getColor(R.color.draw_red))
                }

                else -> null
            }
        }

    }

    private fun onClick(post: Post, view: View?) {
        if (post.tipo == "entrada") {
            val intent = Intent(activity, EntradaActivity::class.java)
            activity.startActivityForResult(intent, 0)
            activity.overridePendingTransition(R.anim.lefttoright, R.anim.stable)
        }
    }
}