package com.soumayaguenaguen.guess

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.os.Bundle
import android.os.CountDownTimer

import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.soumayaguenaguen.guess.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var button: Button
    private lateinit var timer: CountDownTimer
    private var count = 0
    private var isGameOver = false
    private var points = 100
    // Declare a timerIsRunning flag
    private var timerIsRunning = false
    private lateinit var textViewGuess: TextView
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

        // Retrieve the saved name from SharedPreferences
        val name = sharedPreferences.getString("name", "")

        // Display the saved name in a Toast
        if (!name.isNullOrEmpty()) {
            Toast.makeText(this, "Welcome back, $name!", Toast.LENGTH_SHORT).show()
        }

        //val btnback: ImageButton = findViewById(R.id.Back_Button)//
        textViewGuess = findViewById(R.id.textViewGuess)


        val r = Random.nextInt(0 until 100)


        button = binding.textView
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                button.text = "" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                val context = this@MainActivity
                val builder = AlertDialog.Builder(context)


                builder.setMessage("Oops Time is Up!")

                    .setCancelable(true)

                    .setPositiveButton("Close") { dialog, id ->
                        NonCancellable.cancel()
                    }


                val alert = builder.create()

                alert.setTitle("Do you need help?")

                alert.show()


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
    fun checkNumbers(userNumber: Int, guess: Int) {
        points--
        count++
        if (guess > userNumber) {
            binding.textViewHint.text = "Choose a higher number"
        } else if (guess < userNumber) {
            binding.textViewHint.text = "Choose a lower number"
        } else {
            binding.textViewHint.text = "Congratulations you have found it in $count tries."
            binding.buttonViewScore.text = "$points"
            binding.buttonGuess.text = "Start a new game"
            isGameOver = true
            timer.cancel()
            val editor = sharedPreferences.edit()
            editor.putInt("points", points)
            editor.putInt("count", count)
            editor.apply()


            val context = this@MainActivity
            val builder = AlertDialog.Builder(context)


            builder.setMessage("Congratulations! You have won the game.")
                .setCancelable(false)
                .setPositiveButton("See results") { dialog, id ->
                    // Launch new activity to show game results
                    val intent = Intent(this, ResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            val alert = builder.create()

            alert.setTitle("Game Over")
            alert.show()
        }


        if (!timerIsRunning) {

            startTimer()
            timerIsRunning = true
        }


        binding.buttonViewScore.text = "$points"
        textViewGuess.text = "Your last guess: $userNumber"
        binding.editTextNumber.text!!.clear()
    }



    private fun startTimer() {

        timer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                button.text = "" + millisUntilFinished / 1000
            }


            override fun onFinish() {
                timerIsRunning = false
                val context = this@MainActivity
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Oops Time is Up!")
                    .setCancelable(true)
                    .setPositiveButton("Close") { dialog, id ->
                        NonCancellable.cancel()
                    }


                val alert = builder.create()
                alert.setTitle("Game Over")
                alert.show()
            }
        }.start()
    }
}
