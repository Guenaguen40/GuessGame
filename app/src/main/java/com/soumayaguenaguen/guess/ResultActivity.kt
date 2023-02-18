package com.soumayaguenaguen.guess

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
class ResultActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultsListView = findViewById<ListView>(R.id.results_listview)
        val resultsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        resultsListView.adapter = resultsAdapter

        sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val resultsList = ArrayList<String>()
        val resultsCount = sharedPreferences.getInt("results_count", 0)

        for (i in 0 until resultsCount) {
            val result = sharedPreferences.getString("result_$i", null)
            if (result != null) {
                val parts = result.split(":")
                val name = parts[0]
                val points = parts[1]
                val count = parts[2]
                resultsList.add("$name - Points: $points - Count: $count")
            }
        }


        if (resultsList.isNotEmpty()) {
            resultsAdapter.addAll(resultsList)
        } else {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
        }
    }
}
