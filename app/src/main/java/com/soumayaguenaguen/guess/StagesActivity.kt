package com.soumayaguenaguen.guess

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class StagesActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stages)

        val button1: Button = findViewById(R.id.buttonStage_Easy)
        val stage3: Button = findViewById(R.id.buttonStage_Hard)

        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        stage3.setOnClickListener {
            val i = Intent(this, ThirdStageActivity::class.java)
            startActivity(i)
        }
    }
}