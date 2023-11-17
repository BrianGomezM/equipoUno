package com.univalle.picobotellamvvm.view.fragment

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.univalle.picobotellamvvm.databinding.FragmentHomeBinding
import com.univalle.picobotellamvvm.view.dialog.DeleteDialog
import com.univalle.picobotellamvvm.viewmodel.PokemonViewModel
import com.univalle.picobotellamvvm.R
import com.univalle.picobotellamvvm.viewmodel.ChallengeViewModel
import com.univalle.picobotellamvvm.viewmodel.SoundViewModel
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var contador: CountDownTimer? = null
    private lateinit var mediaPlayer: MediaPlayer
    private val pokemonViewModel: PokemonViewModel by viewModels()
    private val challengeViewmodel: ChallengeViewModel by viewModels()
    private lateinit var soundViewModel: SoundViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        pokemonViewModel.getPokemones()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupToolbar()
        super.onViewCreated(view, savedInstanceState)
        soundViewModel = ViewModelProvider(requireActivity()).get(SoundViewModel::class.java)

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

        soundIcon()
    }

    private fun soundIcon() {
        val sound = view?.findViewById<ImageView>(R.id.sound)

        val icon = if (soundViewModel.soundPlaying)
            R.drawable.baseline_volume_up_24 else R.drawable.baseline_volume_off_24

        sound?.setImageResource(icon)
    }

    private fun soundPlay() {
        if (soundViewModel.soundPlaying) mediaPlayer.start() else mediaPlayer.pause()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        if (soundViewModel.soundPlaying) {
            mediaPlayer.start()
        }
    }

    private fun iniciarContador() {

        mediaPlayer.pause()
        val bottlePlayer=MediaPlayer.create(requireContext(), R.raw.bottlesfx)
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

        var counter = 3
        binding.contadorTextView.visibility = View.VISIBLE
        val timer = Timer()
        val timertask = object : TimerTask() {
            override fun run() {
                counter--
                val mainHandler = Handler(Looper.getMainLooper())
                mainHandler.post {
                    if (counter >= 0) {
                        binding.contadorTextView.text = counter.toString()
                    } else {
                        binding.contadorTextView.visibility = View.INVISIBLE
                        counter = 3
                        binding.contadorTextView.text = counter.toString()

                        soundPlay()
                        showChallenge()
                        timer.cancel()
                    }
                }
            }
        }
        timer.schedule(timertask, 500L, 1500L)
    }

    private fun showChallenge(){
        val challengeDialog = AlertDialog.Builder(requireContext())
        challengeDialog.setView(R.layout.random_pokemon)
        val dialog = challengeDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()
        pokemonViewModel.pokemonResponse.observe(viewLifecycleOwner) { response ->
            if (response != null && response.isSuccessful) {
                val pokemonList = response.body()?.pokemon
                if (!pokemonList.isNullOrEmpty()) {
                    val randomIndex = (pokemonList.indices).random()
                    val imageUrl = pokemonList[randomIndex].image
                    val imageView = dialog.findViewById<ImageView>(R.id.roundedPokemon)
                    if (imageView != null) {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .override(275,275)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                            .into(imageView)
                    }
                } else {
                    Log.e("Viewmodel", "empty or null pokemon list")
                }
                }else {
                    Log.e("Viewmodel","Error in response: ${response?.message()}")
                }

            }

            val textviewReto = dialog.findViewById<TextView>(R.id.textViewReto)
            challengeViewmodel.getRandomChallenge()
            challengeViewmodel.randomChallenge.observe(viewLifecycleOwner){challenge ->
                if (challenge != null){
                    textviewReto?.text = challenge.descriptionChallenge
                } else{
                    Log.d("challenge", "No hay challenges")
                }
            }

            val closeButton = dialog.findViewById<Button>(R.id.buttonCerrarRandomChallenge)
            closeButton?.setOnClickListener {
                binding.botonPresioname.animation?.start()
                binding.botonPresioname.visibility = View.VISIBLE
                binding.botonPresioname.isEnabled = true
                dialog.dismiss()
            }
        }

    //Brayan G
    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.contentToobar.toolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.contentToobar.toolbar.apply {
            setupShareButton()
            setupSoundButton()
            setupRatingsButton()
            setupInstrucciones()
            setupChallenges()
        }

    }

    private fun View.setupRatingsButton(){
        val ratingClick = findViewById<ImageView>(R.id.ratings)
        ratingClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.appUrl)))
            startActivity(intent)
        }
    }

    private fun View.setupSoundButton() {
        val soundClick = findViewById<ImageView>(R.id.sound)

        soundClick.setOnClickListener {
            soundViewModel.soundPlaying = !soundViewModel.soundPlaying
            soundIcon()
            soundPlay()
        }
    }

    private fun View.setupInstrucciones() {
        val ratingClick = findViewById<ImageView>(R.id.instrucciones)
        ratingClick.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_instruccionesFragment)
        }
    }

    private fun View.setupChallenges() {
        val challengesClick = findViewById<ImageView>(R.id.challenge)
        challengesClick.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_challenges)
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

}