package com.univalle.picobotellamvvm.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clase6.Dialogos.DialogoPersonalizado.Companion.showDialogPersonalizado
import com.univalle.picobotellamvvm.view.dialog.EditDialogo
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
            challengeAdapter  = ChallengeAdapter(listaChallenge, { position, descriptionChallenge ->
                showDeleteDialog(position, descriptionChallenge)},{position,descriptionChallenge-> showEditDialog(position,descriptionChallenge)
            })
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


    ////////////////////////////////////////***////////////////////////////////////////////////////
    private fun editChallenge(position: Int, descriptionChallengetoEdit: String ){
        /**
        var challengeToEdit = challengeViewModel.listChallenge.value?.get(position)
        challengeToEdit?.descriptionChallenge=descriptionChallengetoEdit
        */
        val newChallenge = Challenge(position, descriptionChallengetoEdit)
        if (newChallenge!=null){
            challengeViewModel.editChallenge(newChallenge)
            challengeAdapter?.notifyDataSetChanged()
        }


    }

    private fun showEditDialog(position: Int,descriptionChallenge: String){
        EditDialogo.showEditDialogReal(binding.root.context, {
            observerViewModel()
        },position+1,descriptionChallenge)

        /**

        val editDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.editar_reto, null)



        val dialog = AlertDialog.Builder(requireContext())
            .setView(editDialogView)
            .create()


        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

        //setup textfield
        val textEditField = editDialogView.findViewById<EditText>(R.id.editarReto)
        textEditField.setText(descriptionChallenge)



        //listeners
        val cancelEditButton = editDialogView.findViewById<AppCompatButton>(R.id.canceledit_button)
        cancelEditButton.setOnClickListener {

            dialog?.dismiss()
        }
        val editButton = editDialogView.findViewById<AppCompatButton>(R.id.edit_button)
        editButton.setOnClickListener{
            editChallenge(position,descriptionChallenge)
            dialog?.dismiss()
        }


        dialog.show()
        */

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