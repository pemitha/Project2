package com.example.project_2

//import kotlinx.android.synthetic.main.fragment_bottom.*
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var colorAnimation:ObjectAnimator

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


        updateDateTime()
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
}
