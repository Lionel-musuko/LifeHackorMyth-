package com.example.lifehackormyth

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReviewActivity : AppCompatActivity() {

    private val TAG = "ReviewActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val answers = intent.getBooleanArrayExtra("ANSWERS") ?: BooleanArray(0)
        val builder = StringBuilder()

        // Loop through every hack and list the statement, answer and explanation
        for ((index, hack) in HackRepository.hacks.withIndex()) {
            val correctLabel = if (hack.isTrue) "Hack (True)" else "Myth (False)"
            val yourLabel = if (answers.getOrElse(index) { false }) "Hack (True)" else "Myth (False)"
            builder.append("${index + 1}. ${hack.statement}\n")
            builder.append("Correct answer: $correctLabel\n")
            builder.append("Your answer: $yourLabel\n")
            builder.append("${hack.explanation}\n\n")
        }
        Log.d(TAG, "Review built for ${HackRepository.hacks.size} questions")

        findViewById<TextView>(R.id.tvReview).text = builder.toString()
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }
}