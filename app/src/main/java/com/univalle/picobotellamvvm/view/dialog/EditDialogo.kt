package com.univalle.picobotellamvvm.view.dialog

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.univalle.picobotellamvvm.databinding.EditarRetoBinding
import com.univalle.picobotellamvvm.model.Challenge
import com.univalle.picobotellamvvm.viewmodel.ChallengeViewModel

class EditDialogo{
    companion object {

        fun showEditDialogReal(
            context: Context,
            onDialogDismissed: () -> Unit
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = EditarRetoBinding.inflate(inflater)

            val challengeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
                .create(ChallengeViewModel::class.java)

            val listEditText = listOf(binding.editarReto)//Textfield

            for (editText in listEditText) {
                editText.addTextChangedListener {
                    val isListFull = listEditText.all {
                        it.text.isNotEmpty()
                    }
                    binding.editButton.isEnabled = isListFull
                }
            }
            ////////////////////////
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            binding.editButton.setOnClickListener {
                //saveChallenge(challengeViewModel, binding)
                val posicion=0
                val reto = binding.editarReto.text.toString()
                val challenge = Challenge(posicion,descriptionChallenge = reto)
                challengeViewModel.editChallenge(challenge)

                Toast.makeText(context, "Reto editado", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
                onDialogDismissed()
            }
            binding.canceleditButton.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }
}