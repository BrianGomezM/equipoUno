package com.univalle.picobotellamvvm

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.univalle.picobotellamvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var contador: CountDownTimer? = null
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val botonIniciar = findViewById<Button>(R.id.boton_presioname)
        botonIniciar.setOnClickListener {
            iniciarContador()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.st)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
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
        })}
        private fun iniciarContador() {
            binding.contadorTextView.text = "3"
            contador = object : CountDownTimer(4000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = (millisUntilFinished / 1000).toInt()
                    binding.contadorTextView.text = seconds.toString()

                }

                override fun onFinish() {
                    // Lógica cuando el contador llega a 0
                }
            }

            contador?.start()
    }

    }
