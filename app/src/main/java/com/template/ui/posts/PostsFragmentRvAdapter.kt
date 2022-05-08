package com.template.ui.posts

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.template.R

class PostsFragmentRvAdapter(private val content: Array<String>,
                             private val images: HashMap<String, Bitmap>,
                             private val clickListener: OnItemClick):
    RecyclerView.Adapter<PostsFragmentRvAdapter.F1FragmentViewHolder>() {

    class F1FragmentViewHolder(itemView: View): ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): F1FragmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_posts_menu_item, parent, false)
        return F1FragmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: F1FragmentViewHolder, position: Int) {
        holder.textView.setText(content[position])
        holder.imageButton.setImageBitmap(images.get(content[position]))
        holder.imageButton.setOnClickListener({ v->
            clickListener.onItemClick(content[position])
        })
    }

    override fun getItemCount(): Int {
        return content.size
    }
}