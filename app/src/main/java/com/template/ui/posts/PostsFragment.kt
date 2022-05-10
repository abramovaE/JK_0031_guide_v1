package com.template.ui.posts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.template.MainActivityViewModel
import com.template.R
import com.template.databinding.FragmentPostsBinding
import com.template.ui.post.PostFragment

import kotlin.collections.HashMap

class PostsFragment : Fragment(), OnItemClick {

    private var _binding: FragmentPostsBinding? = null

    private val binding get() = _binding!!
    private lateinit var fileName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainActivityViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rv = binding.rv
        val postIndex = arguments?.getInt("postsIndex")

        val assets = requireActivity().assets.list("content")
        val fileName = postIndex?.let {
            assets?.get(it)
        }
        fileName?.let { mainActivityViewModel.postTitle(it) }
        if (fileName != null) {
            this.fileName = fileName
        }
        val file = requireActivity().assets.list("content/${fileName}")
        val map = mutableMapOf<String, Bitmap>()
        val posts = mutableListOf<String>()
        if (file != null) {
            for(f in file){
                if(!f.contains("img")){
                    posts.add(f)
                    val bytes = requireActivity().assets.open("content/${fileName}/$f" + "/image.png").readBytes()
                    val postImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    map[f] = postImage
                }
            }
        }
        rv.adapter = file?.let { PostsFragmentRvAdapter(posts.toTypedArray(),
            map as HashMap<String, Bitmap>, this) }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(fileName: String) {
        val file = "content/${this.fileName}/${fileName}"
        val postFragment = PostFragment()
        val arguments = Bundle()
        arguments.putString("postPath", file)
        arguments.putString("title", fileName)
        postFragment.arguments = arguments
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, postFragment).commit()
    }
}

interface OnItemClick{
    fun onItemClick(fileName: String)
}