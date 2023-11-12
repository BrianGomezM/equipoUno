package com.univalle.picobotellamvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.univalle.picobotellamvvm.databinding.FragmentHomeBinding
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

    }




}