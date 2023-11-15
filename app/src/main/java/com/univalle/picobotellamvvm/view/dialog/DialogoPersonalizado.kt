package com.example.clase6.Dialogos

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.univalle.picobotellamvvm.databinding.DialogoPersonalizadoBinding
import com.univalle.picobotellamvvm.model.Challenge
import androidx.lifecycle.ViewModelProvider
import com.univalle.picobotellamvvm.viewmodel.ChallengeViewModel

class DialogoPersonalizado {
    companion object {

        fun showDialogPersonalizado(
            context: Context,
            onDialogDismissed: () -> Unit
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = DialogoPersonalizadoBinding.inflate(inflater)

            val challengeViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
                .create(ChallengeViewModel::class.java)

            val listEditText = listOf(binding.etReto)

            for (editText in listEditText) {
                editText.addTextChangedListener {
                    val isListFull = listEditText.all {
                        it.text.isNotEmpty()
                    }
                    binding.saveButton.isEnabled = isListFull
                }
            }

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            binding.saveButton.setOnClickListener {
                //saveChallenge(challengeViewModel, binding)

                val reto = binding.etReto.text.toString()
                val challenge = Challenge(descriptionChallenge = reto)
                challengeViewModel.saveChallenge(challenge)

                Toast.makeText(context, "Reto guardado", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
                onDialogDismissed()
            }
            alertDialog.show()
        }

        private fun saveChallenge(challengeViewModel: ChallengeViewModel, binding: DialogoPersonalizadoBinding) {
            val reto = binding.etReto.text.toString()
            val challenge = Challenge(descriptionChallenge = reto)
            challengeViewModel.saveChallenge(challenge)
        }
    }
}