package com.template.ui.exit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.template.databinding.FragmentExitBinding

class ExitFragment : Fragment() {

    private var _binding: FragmentExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExitBinding.inflate(inflater, container, false)
        val root: View = binding.root
        requireActivity().finish()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}