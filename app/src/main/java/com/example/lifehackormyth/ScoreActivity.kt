package com.example.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    private val TAG = "ScoreActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val score = intent.getIntExtra("SCORE", 0)
        val answers = intent.getBooleanArrayExtra("ANSWERS") ?: BooleanArray(0)
        val total = HackRepository.hacks.size
        Log.d(TAG, "Score screen: $score / $total")

        findViewById<TextView>(R.id.tvScore).text = "You scored $score / $total"

        // Personalised feedback based on the score
        val feedback = when {
            score >= total * 0.8 -> "Master Hacker!"
            score >= total * 0.5 -> "Great job!"
            else -> "Stay Safe Online! Keep practising!"
        }
        findViewById<TextView>(R.id.tvScoreFeedback).text = feedback

        // Review button opens the list of statements and explanations
        findViewById<Button>(R.id.btnReview).setOnClickListener {
            Log.d(TAG, "Review clicked")
            val reviewIntent = Intent(this, ReviewActivity::class.java)
            reviewIntent.putExtra("ANSWERS", answers)
            startActivity(reviewIntent)
        }

        // Restart goes back to the welcome screen with a fresh quiz
        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            Log.d(TAG, "Play again clicked")
            val restartIntent = Intent(this, MainActivity::class.java)
            restartIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(restartIntent)
            finish()
        }
    }
}