package com.example.project_2

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class BottomFragment : Fragment() {
    private lateinit var seasonalImageView: ImageView
    private lateinit var dateTimeTextView: TextView
    private lateinit var topSpinner: ConstraintLayout
    private lateinit var imageViewSpinner: ImageView
    lateinit var colorAnimation:ObjectAnimator

    private var isSpinning = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTimeTextView = view.findViewById(R.id.dateTimeTextView)
        seasonalImageView = view.findViewById(R.id.seasonalImageView)
        topSpinner = view.findViewById(R.id.topSpinner)
        imageViewSpinner = view.findViewById(R.id.wheelSpinner)

        startSpinnerAnimation()
        updateDateTime()
        setSeasonImage(getCurrentSeason())
    }

    private fun startSpinnerAnimation() {
        isSpinning = true
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = Animation.INFINITE
        imageViewSpinner.startAnimation(rotateAnimation)
    }

    fun stopSpinnerAnimation() {
        isSpinning = false
        imageViewSpinner.clearAnimation()
    }

    fun setSeasonImage(imageResId: Int) {
        // Fade in animation
        val fadeInAnimation: ObjectAnimator =
            ObjectAnimator.ofFloat<View>(seasonalImageView, View.ALPHA, 0f, 1f)
        fadeInAnimation.duration = 1000 // in milliseconds

        // Fade out animation
        val fadeOutAnimation: ObjectAnimator =
            ObjectAnimator.ofFloat<View>(seasonalImageView, View.ALPHA, 1f, 0f)
        fadeOutAnimation.duration = 1000

        fadeOutAnimation.start()
        fadeInAnimation.start()
        seasonalImageView.setImageResource(imageResId)
    }

    fun animateBackgroundColor(fromColor: Int, toColor: Int) {
        colorAnimation = ObjectAnimator.ofObject(
            topSpinner,
            "backgroundColor",
            ArgbEvaluator(),
            fromColor,
            toColor
        )
        colorAnimation.duration = 5000 // 5 seconds
        colorAnimation.start()
    }

    private fun updateDateTime() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                dateTimeTextView.text = getCurrentDateTime()
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    private fun getCurrentSeason(): Int {
        val month = Calendar.getInstance().get(Calendar.MONTH)
        return when (month) {
            in 0..1, 11 -> R.drawable.winter
            in 2..4 -> R.drawable.spring
            in 5..7 -> R.drawable.summer
            in 8..10 -> R.drawable.autumn
            else -> R.drawable.spring // fallback image
        }
    }
}
