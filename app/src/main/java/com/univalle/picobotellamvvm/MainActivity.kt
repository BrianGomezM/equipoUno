package com.univalle.picobotellamvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.univalle.picobotellamvvm.Dialogs.DeleteDialog
import com.univalle.picobotellamvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.contentToobar.toolbar)

        binding.contentToobar.toolbar.apply {
            setupShareButton()
            setupPruebaButton()
        }
    }

    private fun View.setupShareButton() {
        val shareImageView = findViewById<ImageView>(R.id.share)
        shareImageView.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val title = getString(R.string.title)
            val slogan = getString(R.string.slogan)
            val appUrl = getString(R.string.appUrl)
            val message = "$title\n$slogan\n$appUrl"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.titleSistema)))
        }
    }

    private fun View.setupPruebaButton() {
        val pruebaImageView = findViewById<ImageView>(R.id.prueba)
        pruebaImageView.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        val mensajeReto = "hola esto es un reto"
        val idReto = 1
        val dialog = DeleteDialog.showDialog(binding.root.context, idReto, mensajeReto)
        dialog.show()
    }
}
