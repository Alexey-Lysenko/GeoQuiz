package com.bignerdrunch.android.geoname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton:  Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) quizViewModel.correctAnswerCount++
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
        if (quizViewModel.currentIndex == quizViewModel.questionBank.size-1) {
            Toast.makeText(this, "${
                quizViewModel.correctAnswerCount.toDouble() / quizViewModel.questionBank.size.toDouble() *100
            }%",
                Toast.LENGTH_LONG).show()
            quizViewModel.correctAnswerCount = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }

        nextButton.setOnClickListener {
            quizViewModel.prevIndex = quizViewModel.currentIndex
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
        updateQuestion()

        prevButton.setOnClickListener {
            quizViewModel.currentIndex = quizViewModel.prevIndex
            updateQuestion()
        }
        questionTextView.setOnClickListener {
            nextButton.callOnClick()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }
}

