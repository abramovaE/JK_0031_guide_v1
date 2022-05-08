package com.template.ui.post

import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.template.R
import com.template.databinding.FragmentPostBinding
import com.template.ui.main_menu.MainMenuFragment

class PostFragment : Fragment() {

    private lateinit var galleryViewModel: PostViewModel
    private var _binding: FragmentPostBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root



        var imagePath = arguments?.getString("imagePath", "post")
        var textPath = arguments?.getString("textPath", "text")

        Log.d("TAG", "textPath: $textPath")


        var textContent = textPath?.let {
            var stream = requireActivity().assets.open(it + "/post")
            stream.bufferedReader().use {
                it.readText()
            }
        }

        var imageContent = imagePath?.let {
            val bytes = requireActivity().assets.open(it + "/image.png").readBytes()
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        val textView: TextView = binding.textView
        textView.text = textContent

        val image = binding.image
        image.setImageBitmap(imageContent)

        binding.menuBtn.setOnClickListener({
            val menuFragment = MainMenuFragment()
            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, menuFragment).commit()
        })
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}