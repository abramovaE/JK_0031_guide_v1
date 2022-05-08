package com.template.ui.posts

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

class PostsFragment : Fragment(), OnItemClick {


    private lateinit var postsViewModel: PostsViewModel
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
            Log.d("TAG", "file: $file")
            rv.adapter = file?.let { PostsFragmentRvAdapter(file, this) }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        Log.d("TAG", "onItemClick: $position")
        Log.d("TAG", "onItemClick fileName: $fileName")

        var file = requireActivity().assets.list("content/${fileName}")
        var post = file?.get(position)

        Log.d("TAG", "onItemClick post: $post")
        var postFragment = PostFragment()
        var arguments = Bundle();
        var textPath = "content/${fileName}/$post"

        Log.d("TAG", "onItemClick textPath: $textPath")

        arguments.putString("imagePath", textPath)
        arguments.putString("textPath", textPath)
        postFragment.arguments = arguments
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, postFragment).commit()
    }
}

interface OnItemClick{
    fun onItemClick(position: Int)
}