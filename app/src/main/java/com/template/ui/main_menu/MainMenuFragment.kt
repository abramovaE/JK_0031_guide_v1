package com.template.ui.main_menu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
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

    private lateinit var homeViewModel: MainMenuViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var _binding: FragmentMainMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(MainMenuViewModel::class.java)
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rv: RecyclerView = binding.rv

        val parts = requireActivity().assets.list("content")

        val images = mutableListOf<Bitmap>()
        if (parts != null) {
            for((index, element) in parts.withIndex()){

                val imgPath = requireActivity().assets.list( "content/$element")?.get(index)
                Log.d("TAG", "log: " + imgPath)

                val bytes = requireActivity().assets.open("content/$element/part_img.png").readBytes()

//                val bytes = imgPath?.let { requireActivity().assets.open(it).readBytes() }

                val bitmap = bytes?.let { BitmapFactory.decodeByteArray(bytes, 0, it.size) }
                if (bitmap != null) {
                    images.add(bitmap)
                }
            }
        }

        val adapter = MainMenuRvAdapter(parts as Array<String>,
            images, requireContext(), this)
        rv.adapter = adapter
        return root
    }


    override fun onItemClick(position: Int){
        Log.d("TAG", "onItemClick: $position")
        var postsFragment = PostsFragment()
        mainActivityViewModel.postPostsIndex(position)

        var arguments = Bundle();
        arguments.putInt("postsIndex", position)
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
    fun onItemClick(position: Int)
}