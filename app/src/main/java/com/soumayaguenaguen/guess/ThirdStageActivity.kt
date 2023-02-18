package com.soumayaguenaguen.guess

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.soumayaguenaguen.guess.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class ThirdStageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var button: Button
    private lateinit var timer: CountDownTimer
    private var count = 0
    private var isGameOver = false
    private var points = 100
    // Declare a timerIsRunning flag
    private var timerIsRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_stage)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val btnback: ImageButton = findViewById(R.id.Back_Button)//

        //guess game code
        val r = Random.nextInt(0 until 100)

        //timer code
        button = binding.textView
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                button.text = "" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                // handle timer finished


            }
        }

        binding.buttonGuess.setOnClickListener {
            if (isGameOver) {
                recreate()
            }

            if (binding.editTextNumber.text!!.isNotEmpty()) {
                val userNum = binding.editTextNumber.text.toString().toInt()
                if (userNum in 1..99) {
                    checkNumbers(userNum, r)
                } else {
                    Toast.makeText(
                        this,
                        "Please enter a number between 1 to 99",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Please enter your guess", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    fun checkNumbers(userNumber : Int, guess : Int){
        points--
        count++
        if (guess > userNumber){
            binding.textViewHint.text = "Choose a higher number"

        }else if(guess < userNumber){
            binding.textViewHint.text = "Choose a lower number"

        }else{
            binding.textViewHint.text = "Congratulations you have found it in $count tries."
            binding.buttonViewScore.text = "$points"
            binding.buttonGuess.text = "Start a new game"
            isGameOver = true
            timer.cancel()
        }
        // Check if the timer is running
        if (!timerIsRunning) {
            // Start the timer if it's not running
            startTimer()
            timerIsRunning = true
        }

        // Update the score display to reflect the new value of points
        binding.buttonViewScore.text = "$points"
        binding.editTextNumber.text!!.clear()
    }



    private fun startTimer() {
        // Initialize the timer
        timer = object : CountDownTimer(30000, 1000) {
            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                button.text = "" + millisUntilFinished / 1000
            }

            // Callback function, fired when the time is up
            override fun onFinish() {
                timerIsRunning = false
                // Show a toast message or perform any other action when the timer finishes
            }
        }.start()
    }
}