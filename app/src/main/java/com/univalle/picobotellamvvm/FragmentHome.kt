package com.univalle.picobotellamvvm

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.univalle.picobotellamvvm.databinding.FragmentHomeBinding
import com.univalle.picobotellamvvm.view.dialog.DeleteDialog
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

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

        mediaPlayer.pause()
        val bottlePlayer=MediaPlayer.create(requireContext(),R.raw.bottlesfx)
        bottlePlayer.start()

        binding.botonPresioname.isEnabled=false
        binding.botonPresioname.visibility=View.GONE
        binding.botonPresioname.clearAnimation()
        val imageBottle = binding.imageView
        val bottlePointer = Random.nextFloat()*360 + (imageBottle.rotation+1800)

        val valueAnimator = ValueAnimator.ofFloat(imageBottle.rotation,bottlePointer)

        valueAnimator.addUpdateListener { animation ->
            val animatedValue=animation.animatedValue as Float
            imageBottle.rotation=animatedValue
        }

        valueAnimator.addListener(object :Animator.AnimatorListener{
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {}
            //contador cuando se acaba la animacion
            override fun onAnimationEnd(animation: Animator) {
                //mediaPlayer.stop()
                bottlePlayer.stop()
                startCountDown()
            }
        })

        //interpolador
        valueAnimator.interpolator=LinearInterpolator()
        //duracion del giro
        valueAnimator.duration=3000
        //inicializacion del giro
        valueAnimator.start()




    }
    private fun startCountDown() {


        var counter = 4
        binding.contadorTextView.visibility = View.VISIBLE
        val timer = Timer()
        val timertask = object : TimerTask() {
            override fun run() {
                counter--
                val mainHandler = Handler(Looper.getMainLooper())
                mainHandler.post {
                    if (counter > 0) {

                        binding.contadorTextView.text = counter.toString()
                    } else {
                        binding.contadorTextView.visibility = View.INVISIBLE
                        counter = 4
                        //spinButton.isEnabled=true

                            mediaPlayer.start()

                            showChallenge()


                        timer.cancel()
                    }
                }
            }
        }
        timer.schedule(timertask, 1000L, 4000L)
    }
    private fun showChallenge(){
        val challengeDialog=AlertDialog.Builder(requireContext())
        challengeDialog.setTitle("Reto #0000")
        challengeDialog.setMessage("Hola Mundo")
        challengeDialog.setPositiveButton("Aceptar"){ dialog, which ->

            binding.botonPresioname.animation?.start()
            binding.botonPresioname.visibility = View.VISIBLE
            binding.botonPresioname.isEnabled = true
            dialog.dismiss()

        }
        val dialog = challengeDialog.create()
        dialog.show()

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
            setupInstrucciones()
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

    private fun View.setupInstrucciones() {
        val ratingClick = findViewById<ImageView>(R.id.instrucciones)
        ratingClick.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_instruccionesFragment)
        }
    }



}