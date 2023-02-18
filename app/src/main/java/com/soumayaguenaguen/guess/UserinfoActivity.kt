package com.soumayaguenaguen.guess

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText


class UserinfoActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)
        sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val submitButton: Button = findViewById(R.id.submitButton)
        val editTextNumber: EditText = findViewById(R.id.Yourname)

        submitButton.setOnClickListener {
            val name = editTextNumber.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }}
