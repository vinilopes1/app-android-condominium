package com.example.vinicius.condominium.app

import android.app.Activity
import android.content.Context
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vinicius.condominium.R
import com.example.vinicius.condominium.R.drawable.tweet
import com.example.vinicius.condominium.models.Post
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.tweet_row.view.*
import java.security.AccessControlContext

class TweetAdapter2(
        var activity: Activity,
        var context: Context,
        var posts: MutableList<Post>
): RecyclerView.Adapter<TweetAdapter2.ViewHolder>(){

    var cont = 0

    class ViewHolder(itemView: View?):RecyclerView.ViewHolder(itemView) {

        lateinit var rowName: TextView
        lateinit var rowHandle: TextView
        lateinit var rowMin: TextView
        lateinit var rowContent: TextView
//        lateinit var txtItemPost: TextView
        lateinit var rowProfImg: ImageView

        init{
            rowName = itemView!!.findViewById(R.id.name)
            rowHandle = itemView!!.findViewById(R.id.data)
            rowMin = itemView!!.findViewById(R.id.min)
            rowContent = itemView!!.findViewById(R.id.content)
//            txtItemPost = itemView!!.findViewById(R.id.txtLocalizacaoItemTimeline)
//            rowProfImg = itemView!!.findViewById(R.id.profImg)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var context = parent.context
        var inflater = LayoutInflater.from(context)
        lateinit var view: View

        if (posts.get(cont).tipo == "ocorrencia") {
            view = inflater.inflate(R.layout.tweet_row, parent, false)
        } else view = inflater.inflate(R.layout.tweet_row2, parent, false)
        cont++

        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = posts.get(position)

        holder.rowName.text = post.informante.nome
        holder.rowHandle.text = post.data
        holder.rowMin.text = post.tipo
        holder.rowContent.text = post.descricao
 //       holder.txtItemPost.text = "Guarita do Térreo"
//        holder.rowProfImg.text = tweet.conImg

    }

}