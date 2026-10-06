package com.example.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionActivity : AppCompatActivity() {

    private val TAG = "QuestionActivity"
    private val hacks = HackRepository.hacks

    private var currentIndex = 0          // which flashcard we are on
    private var score = 0                 // score counter for correct answers
    private val userAnswers = BooleanArray(hacks.size) // what the user picked, kept for Review
    private var answered = false          // stops double-answering one card

    private lateinit var tvProgress: TextView
    private lateinit var tvStatement: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var btnHack: Button
    private lateinit var btnMyth: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        tvProgress = findViewById(R.id.tvProgress)
        tvStatement = findViewById(R.id.tvStatement)
        tvFeedback = findViewById(R.id.tvFeedback)
        btnHack = findViewById(R.id.btnHack)
        btnMyth = findViewById(R.id.btnMyth)
        btnNext = findViewById(R.id.btnNext)

        btnHack.setOnClickListener { checkAnswer(true) }
        btnMyth.setOnClickListener { checkAnswer(false) }
        btnNext.setOnClickListener { nextQuestion() }

        showQuestion()
    }

    // Displays the current flashcard and resets the screen
    private fun showQuestion() {
        val hack = hacks[currentIndex]
        Log.d(TAG, "Showing question ${currentIndex + 1} of ${hacks.size}")

        tvProgress.text = "Question ${currentIndex + 1} of ${hacks.size}"
        tvStatement.text = hack.statement
        tvFeedback.text = ""
        btnNext.visibility = View.INVISIBLE
        btnHack.isEnabled = true
        btnMyth.isEnabled = true
        answered = false
    }

    // Compares the user's choice to the correct answer and gives feedback
    private fun checkAnswer(userSaidHack: Boolean) {
        if (answered) return
        answered = true

        val hack = hacks[currentIndex]
        userAnswers[currentIndex] = userSaidHack

        if (userSaidHack == hack.isTrue) {
            score++
            tvFeedback.text = "Correct! ${hack.explanation}"
            Log.d(TAG, "Correct answer. Score is now $score")
        } else {
            tvFeedback.text = "Wrong! ${hack.explanation}"
            Log.d(TAG, "Wrong answer. Score stays $score")
        }

        btnHack.isEnabled = false
        btnMyth.isEnabled = false
        btnNext.text = if (currentIndex == hacks.size - 1) "See Results" else "Next"
        btnNext.visibility = View.VISIBLE
    }

    // Moves to the next card, or to the Score screen after the last one
    private fun nextQuestion() {
        currentIndex++
        if (currentIndex < hacks.size) {
            showQuestion()
        } else {
            Log.d(TAG, "Quiz finished. Final score: $score / ${hacks.size}")
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            intent.putExtra("ANSWERS", userAnswers)
            startActivity(intent)
            finish()
        }
    }
}