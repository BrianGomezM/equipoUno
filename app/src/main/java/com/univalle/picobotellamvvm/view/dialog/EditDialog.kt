package com.univalle.picobotellamvvm.view.dialog

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.univalle.picobotellamvvm.databinding.DialogoPersonalizadoBinding
import com.univalle.picobotellamvvm.databinding.EditarRetoBinding
import com.univalle.picobotellamvvm.model.Challenge
import com.univalle.picobotellamvvm.viewmodel.ChallengeViewModel

class EditDialog:DialogFragment() {
    private lateinit var binding: EditarRetoBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = EditarRetoBinding.inflate(layoutInflater)
        val view = binding.root
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater



        binding.editButton.setOnClickListener(){
            dismiss()
        }
        binding.cancelButton.setOnClickListener(){
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}