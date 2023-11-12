package com.univalle.picobotellamvvm

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.univalle.picobotellamvvm.databinding.FragmentHomeBinding
import com.univalle.picobotellamvvm.view.dialog.DeleteDialog

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var contador: CountDownTimer? = null
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupToolbar()
        super.onViewCreated(view, savedInstanceState)

        val parpadeoAnim = AlphaAnimation(1f, 0f)
        parpadeoAnim.duration = 1000 // Duración de cada ciclo de parpadeo en milisegundos
        parpadeoAnim.repeatMode = Animation.REVERSE
        parpadeoAnim.repeatCount = Animation.INFINITE

        val botonIniciar = view.findViewById<Button>(R.id.boton_presioname)

        botonIniciar.setOnClickListener {
            iniciarContador()
        }
        botonIniciar.startAnimation(parpadeoAnim)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.st)
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

private fun iniciarContador() {
    //binding.contadorTextView.text = "3"
    contador = object : CountDownTimer(4000, 100) {
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



    //Brayan G
    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.contentToobar.toolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.contentToobar.toolbar.apply {
            setupShareButton()
            setupShowButton()
            setupRatingsButton()
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

    private fun View.setupShowButton() {
      /*  val pruebaImageView = findViewById<ImageView>(R.id.prueba)
        pruebaImageView.setOnClickListener {
            showDeleteDialog()
        }*/
    }

    private fun showDeleteDialog() {
        val mensajeReto = "hola esto es un reto"
        val idReto = 1
        val dialog = DeleteDialog.showDialog(binding.root.context, idReto, mensajeReto)
        dialog.show()
    }

    private fun View.setupRatingsButton(){
        val ratingClick = findViewById<ImageView>(R.id.ratings)
        ratingClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.appUrl)))
            startActivity(intent)
        }
    }
}