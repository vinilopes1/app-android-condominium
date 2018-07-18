package com.example.vinicius.condominium.infra.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.models.Post
import com.example.vinicius.condominium.utils.VectorDrawableUtils
import com.vipul.hp_hp.timelineview.TimelineView
import kotlinx.android.synthetic.main.item_lista_timeline.view.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
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
        var txtData: TextView
        var txtDescricao: TextView
        var txtHora: TextView
        //var ivIconPost: ImageView

        init {
            txtNomeUsuario = itemView.findViewById(R.id.txtNomeUsuarioItemTimeline)
            txtData = itemView.findViewById(R.id.txtDataItemTimeline)
            txtDescricao = itemView.findViewById(R.id.txtDescricaoItemTimeline)
            txtHora = itemView.findViewById(R.id.txtHoraItemTimeline)
            //ivIconPost = itemView.findViewById(R.id.ivIconeItemTimeline)
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

        var data:String = (post.data).split("-")[0].toString()

        if(7 == 7) {
            data = "${data.get(0)}${data.get(1)}/Jul"
        }


        holder.txtDescricao.text = post.descricao
        holder.txtData.text = data
        holder.txtNomeUsuario.text = post.informante.nome
        holder.txtHora.text = (post.data).split("-")[1].split(" ")[1].toString()
        if(post.tipo == "ocorrencia")
            holder.itemView.timeline.setMarker(VectorDrawableUtils.getDrawable(context,R.drawable.ic_sms_failed2_black_24dp))
        else holder.itemView.timeline.setMarker(VectorDrawableUtils.getDrawable(context,R.drawable.ic_input2_black_24dp))

    }
}

