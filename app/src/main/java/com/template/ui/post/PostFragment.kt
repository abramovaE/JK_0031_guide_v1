package com.template.ui.post

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.template.MainActivityViewModel
import com.template.R
import com.template.databinding.FragmentPostBinding
import com.template.ui.main_menu.MainMenuFragment

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        val postPath = arguments?.getString("postPath", "post")
        val title = arguments?.getString("title", "post")

        title?.let { mainActivityViewModel.postTitle(it) }

        val textView: TextView = binding.textView
        val image = binding.image
            val textContent = postPath?.let { it ->
                val stream = requireActivity().assets.open("$it/post")
                stream.bufferedReader().use {
                    it.readText()
                }
            }
            textView.text = textContent
            val imageContent = postPath?.let {
                val bytes = requireActivity().assets.open("$it/image.png").readBytes()
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
            image.setImageBitmap(imageContent)

        binding.menuBtn.setOnClickListener {
            val menuFragment = MainMenuFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, menuFragment).commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}