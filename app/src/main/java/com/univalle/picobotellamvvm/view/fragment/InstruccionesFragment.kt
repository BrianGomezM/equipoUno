package com.univalle.picobotellamvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.univalle.picobotellamvvm.R
import com.univalle.picobotellamvvm.databinding.FragmentInstruccionesBinding


class InstruccionesFragment : Fragment() {
    private lateinit var binding:FragmentInstruccionesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstruccionesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar(){
        binding.contentToolbar.toolbar2.setNavigationOnClickListener{ onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_instruccionesFragment_to_fragmentHome)
    }
}




