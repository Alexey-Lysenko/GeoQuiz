package com.bignerdrunch.android.geoname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(Question(R.string.question_australia,true),
                                      Question(R.string.question_oceans,true),
                                      Question(R.string.question_mideast,false),
                                      Question(R.string.question_africa,false),
                                      Question(R.string.question_americas,true),
                                      Question(R.string.question_asia,true)
    )
    private var currentIndex = 0
    private var prevIndex = 0
    private var correctAnswerCount = 0
    private lateinit var trueButton:  Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) correctAnswerCount++
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
        if (currentIndex == questionBank.size-1) {
            Toast.makeText(this, "${(correctAnswerCount.toDouble() % currentIndex.toDouble())*10}%",
                Toast.LENGTH_LONG).show()
            correctAnswerCount = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
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
            prevIndex = currentIndex
            currentIndex =(currentIndex + 1) % questionBank.size
            updateQuestion()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
        updateQuestion()

        prevButton.setOnClickListener {
            currentIndex = prevIndex
            updateQuestion()
        }
        questionTextView.setOnClickListener {
            nextButton.callOnClick()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG,"onSaveInstanceState() called")
    }
}

