package com.univalle.picobotellamvvm.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clase6.Dialogos.DialogoPersonalizado.Companion.showDialogPersonalizado
import com.univalle.picobotellamvvm.R
import com.univalle.picobotellamvvm.databinding.FragmentChallengesBinding
import com.univalle.picobotellamvvm.model.Challenge
import com.univalle.picobotellamvvm.view.adapter.ChallengeAdapter
import com.univalle.picobotellamvvm.viewmodel.ChallengeViewModel

class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //controladores()
        setupToolbar()
        setupAdd()
        observerViewModel()
    }

    private fun observerViewModel() {
        observerListChallenge()
    }


    private fun observerListChallenge() {
        challengeViewModel.getListChallenge()
        challengeViewModel.listChallenge.observe(viewLifecycleOwner) { listaChallenge ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = ChallengeAdapter(listaChallenge)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupAdd() {
        binding.fbagregar.setOnClickListener {
            showDialogPersonalizado(binding.root.context)
        }
    }

    private fun recycler(){

    }

    /*
    private fun controladores() {
        recycler()
    }

    fun recycler() {

        var listChallenge = mutableListOf(
            Challenge("hola"),
            Challenge("por favor"),
            Challenge("dime"),
            Challenge("que funcionas")
        )

        val recycler = binding.recyclerview
        recycler.layoutManager = LinearLayoutManager(context)
        val adapter = ChallengeAdapter(listChallenge)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }


     */
    private fun setupToolbar() {
        binding.contentToolbar.toolbar2.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_challenges_to_fragmentHome)
    }
}