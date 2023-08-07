package com.example.project_2

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var handler: Handler = Handler()
    private var isRunning = false
    private var currentSeason = 0
    private lateinit var bottomFragment: BottomFragment
    private lateinit var topSegment: ConstraintLayout
    private lateinit var birdsAnimated: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomFragment = BottomFragment()
        topSegment = findViewById(R.id.topSegment)
        birdsAnimated = findViewById(R.id.birds)

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.bottomSegment, bottomFragment)
        transaction.commit()

        startAnimation()

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            startSeasonalAnimations()
        }

        val stopButton = findViewById<Button>(R.id.stopButton)
        stopButton.setOnClickListener {
            stopSeasonalAnimations()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.autumn_song)
    }

    private fun startSeasonalAnimations() {
        isRunning = true
        currentSeason = 0
        updateSeason()
    }

    private fun stopSeasonalAnimations() {
        isRunning = false
        mediaPlayer.stop()
        bottomFragment.stopSpinnerAnimation()
        bottomFragment.animateBackgroundColor(resources.getColor(R.color.white), resources.getColor(R.color.white)) // Reset the background color to white when stopping.
        bottomFragment.setSeasonImage(R.drawable.autumn) // Reset the seasonal image to spring_image when stopping.
    }

    private fun updateSeason() {
        when (currentSeason) {
            0 -> {
                bottomFragment.animateBackgroundColor(resources.getColor(R.color.OrangeRed), resources.getColor(R.color.darkseagreen))
                bottomFragment.setSeasonImage(R.drawable.spring)
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this, R.raw.spring_song)
            }
            1 -> {
                bottomFragment.animateBackgroundColor(resources.getColor(R.color.darkseagreen), resources.getColor(R.color.yellow))
                bottomFragment.setSeasonImage(R.drawable.summer)
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this, R.raw.summer_song)
            }
            2 -> {
                bottomFragment.animateBackgroundColor(resources.getColor(R.color.yellow), resources.getColor(R.color.white))
                bottomFragment.setSeasonImage(R.drawable.autumn)
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this, R.raw.autumn_song)
            }
            3 -> {
                bottomFragment.animateBackgroundColor(resources.getColor(R.color.white), resources.getColor(R.color.OrangeRed))
                bottomFragment.setSeasonImage(R.drawable.winter)
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this, R.raw.winter_song)
            }
        }

        mediaPlayer.start()
        currentSeason = (currentSeason + 1) % 4
        if (isRunning) {
            if(currentSeason == 4){
                currentSeason = 0
            }
            handler.postDelayed({ updateSeason() }, 5000)
        }
    }

    private fun startAnimation() {

        //Birds
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, 1f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        )

        animation.duration = 3000 // Animation duration in milliseconds
        animation.repeatMode = Animation.RESTART
        animation.repeatCount = Animation.INFINITE

        birdsAnimated.startAnimation(animation)
    }
}
