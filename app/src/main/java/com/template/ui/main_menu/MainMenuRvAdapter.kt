package com.template.ui.main_menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.template.R

class MainMenuRvAdapter(private val content: Array<String>,
                        private val images: List<Int>,
                        private val context: Context,
                        private val itemClickListener: OnItemClick
                        ): RecyclerView.Adapter<MainMenuRvAdapter.MainMenuViewHolder>() {


    class MainMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val textView = itemView.findViewById<TextView>(R.id.textView)
        val imageBtn = itemView.findViewById<ImageButton>(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuRvAdapter.MainMenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_main_menu_item, parent,false)
        return MainMenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.textView.text = content[position]
        holder.imageBtn.background = ContextCompat.getDrawable(context, images[position])
        holder.imageBtn.setOnClickListener({ v->
            itemClickListener.onItemClick(position)
        })
    }

    override fun getItemCount(): Int {
        return content.size
    }

    fun getImageId(position: Int): Int {
        var id = "img$position"
        return context.resources.getIdentifier(
            id,
            "drawable",
            "com.template"
        )
    }

}