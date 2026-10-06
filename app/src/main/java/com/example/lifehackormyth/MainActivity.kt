package com.example.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Welcome screen created")

        val btnStart = findViewById<Button>(R.id.btnStart)

        // Start button opens the flashcard question screen
        btnStart.setOnClickListener {
            Log.d(TAG, "Start clicked - launching quiz")
            startActivity(Intent(this, QuestionActivity::class.java))
        }
    }
}