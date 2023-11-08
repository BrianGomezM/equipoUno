package com.univalle.picobotellamvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.univalle.picobotellamvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
    }
    private fun setupToolbar() {
        val toolbar = binding.contentToobar.toolbar
        setSupportActionBar(toolbar)

        // Obtén la referencia al ImageView utilizando el ID
        val shareImageView = toolbar.findViewById<ImageView>(R.id.share)

        // Agrega un OnClickListener al ImageView
        shareImageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // Crear un Intent para compartir texto
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"

                // Agrega el título de la aplicación, el eslogan y la URL
                val title = "App Pico Botella"
                val slogan = "Solo los valientes lo juegan !!"
                val appUrl = "https://play.google.com/store/apps/details?id=com.univalle.picobotellamvvm"

                // Crea el mensaje personalizado
                val message = "$title\n$slogan\n$appUrl"

                // Establece el texto a compartir
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)

                // Muestra el menú de uso compartido
                startActivity(Intent.createChooser(shareIntent, "Compartir a través de"))
            }
        })
    }
}