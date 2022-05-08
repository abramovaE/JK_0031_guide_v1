package com.template.ui.main_menu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.template.MainActivityViewModel
import com.template.R
import com.template.databinding.FragmentMainMenuBinding
import com.template.ui.posts.PostsFragment

class MainMenuFragment : Fragment(), OnItemClick {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var content: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rv: RecyclerView = binding.rv

        val parts = requireActivity().assets.list("content")

        val images = mutableListOf<Bitmap>()
        if (parts != null) {
            for(element in parts){
                val bytes = requireActivity().assets.open("content/$element/part_img.png").readBytes()
                val bitmap = bytes.let { BitmapFactory.decodeByteArray(bytes, 0, it.size) }
                if (bitmap != null) {
                    images.add(bitmap)
                }
            }
        }

        this.content = parts as Array<String>

        val adapter = MainMenuRvAdapter(content,
            images, requireContext(), this)
        rv.adapter = adapter
        return root
    }

    override fun onItemClick(fileName: String){
        val postsFragment = PostsFragment()
        mainActivityViewModel.postTitle(fileName)
        val arguments = Bundle()
        arguments.putString("fileName", fileName)
        postsFragment.arguments = arguments
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, postsFragment).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface OnItemClick{
    fun onItemClick(fileName: String)
}