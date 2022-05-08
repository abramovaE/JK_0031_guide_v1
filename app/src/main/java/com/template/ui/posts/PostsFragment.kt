package com.template.ui.posts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.template.MainActivityViewModel
import com.template.R
import com.template.databinding.FragmentPostsBinding
import com.template.ui.post.PostFragment
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class PostsFragment : Fragment(), OnItemClick {

    private var _binding: FragmentPostsBinding? = null

    private val binding get() = _binding!!
    private lateinit var fileName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rv = binding.rv

        Log.d("TAG", "postsArguments: ${arguments?.getInt("postsIndex")}")
        mainActivityViewModel.postsIndex.observe(viewLifecycleOwner, {
            var assets = requireActivity().assets.list("content")
            var fileName =  assets?.get(it)
            if (fileName != null) {
                this.fileName = fileName
            }
            var file = requireActivity().assets.list("content/${fileName}")
            Log.d("TAG", "file: ${"content/${fileName}"}")

            var map = mutableMapOf<String, Bitmap>()

            var posts = mutableListOf<String>()
            if (file != null) {
                for(f in file){
                    if(!f.contains("img")){
                        posts.add(f)

                        val postName = f

                        val bytes = requireActivity().assets.open("content/${fileName}/$f" + "/image.png").readBytes()
                        val postImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                        map.put(postName, postImage)

                    }
                }
            }

            Log.d("TAG", "posts: $posts")
            Log.d("TAG", "map: $map")



            rv.adapter = file?.let { PostsFragmentRvAdapter(posts.toTypedArray(),
                map as HashMap<String, Bitmap>, this) }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(filename: String) {
        var file = "content/${fileName}/${filename}"
        var postFragment = PostFragment()
        var arguments = Bundle();
        arguments.putString("postPath", file)
        postFragment.arguments = arguments
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, postFragment).commit()
    }
}

interface OnItemClick{
    fun onItemClick(fileName: String)
}