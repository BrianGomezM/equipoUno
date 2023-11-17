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
import com.univalle.picobotellamvvm.view.dialog.DeleteDialog
import com.univalle.picobotellamvvm.viewmodel.ChallengeViewModel

class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()
    private var challengeAdapter: ChallengeAdapter? = null
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
            challengeAdapter  = ChallengeAdapter(listaChallenge) { position, descriptionChallenge ->
                showDeleteDialog(position, descriptionChallenge)
            }
            recycler.adapter = challengeAdapter
        }
    }

    private fun setupAdd() {
        binding.fbagregar.setOnClickListener {
            showDialogPersonalizado(binding.root.context) {
                observerViewModel()
            }
        }
    }
    private fun showDeleteDialog(position: Int, descriptionChallenge: String) {
        val idReto = challengeViewModel.listChallenge.value?.get(position)?.id ?: -1
        val mensajeReto = "Reto: $descriptionChallenge"
        val dialog = DeleteDialog.showDialog(binding.root.context, idReto, mensajeReto) {
            deleteChallenge(position)
        }
        dialog.show()
    }

    private fun deleteChallenge(position: Int) {
        val challengeToDelete = challengeViewModel.listChallenge.value?.get(position)
        if (challengeToDelete != null) {
            challengeViewModel.deleteChallenge(challengeToDelete)
            challengeAdapter?.notifyDataSetChanged()
        }
    }

    private fun setupToolbar() {
        binding.contentToolbar2.toolbarChallenge.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_challenges_to_fragmentHome)
    }
}