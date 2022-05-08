package com.template.ui.main_menu

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.template.R

class MainMenuRvAdapter(private val content: Array<String>,
                        private val images: List<Bitmap>,
                        private val context: Context,
                        private val itemClickListener: OnItemClick
                        ): RecyclerView.Adapter<MainMenuRvAdapter.MainMenuViewHolder>() {

    class MainMenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val textView = itemView.findViewById<TextView>(R.id.textView)
        val imageBtn = itemView.findViewById<ImageButton>(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_main_menu_item, parent,false)
        return MainMenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.textView.text = content[position]
        holder.imageBtn.setImageBitmap(images[position])
        holder.imageBtn.scaleType = ImageView.ScaleType.FIT_CENTER
        holder.imageBtn.background = ContextCompat
            .getDrawable(context, R.drawable.rv_main_menu_item)
        holder.imageBtn.setOnClickListener {
            itemClickListener.onItemClick(content[position])
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }
}