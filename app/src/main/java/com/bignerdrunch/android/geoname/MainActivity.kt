package com.bignerdrunch.android.geoname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf<Question>(Question(R.string.question_australia,true),
                                                Question(R.string.question_oceans,true),
                                                Question(R.string.question_mideast,false),
                                                Question(R.string.question_africa,false),
                                                Question(R.string.question_americas,true),
                                                Question(R.string.question_asia,true)
    )
    private var currentIndex = 0
    private lateinit var trueButton:  Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {checkAnswer(true) }

        falseButton.setOnClickListener {checkAnswer(false)}

        nextButton.setOnClickListener { currentIndex =(currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }


}

