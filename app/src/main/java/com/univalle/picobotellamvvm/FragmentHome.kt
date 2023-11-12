package com.univalle.picobotellamvvm

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import com.univalle.picobotellamvvm.databinding.FragmentHomeBinding

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
}