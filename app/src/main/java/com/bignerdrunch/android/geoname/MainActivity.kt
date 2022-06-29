package com.bignerdrunch.android.geoname

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bignerdrunch.android.geoname.databinding.ActivityMainBinding

private const val KEY_INDEX = "Question_index"

class MainActivity : AppCompatActivity() {


    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private lateinit var binding:ActivityMainBinding

    private val cheatActivityLouncher = registerForActivityResult(CheatActivity.Contract()){
        binding.falseButton.isEnabled = !it
        binding.trueButton.isEnabled = !it
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
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
        if (quizViewModel.currentIndex == quizViewModel.questionBankSize-1) {
            Toast.makeText(this, "${
                quizViewModel.correctAnswerCount.toDouble() / quizViewModel.questionBankSize.toDouble() *100
            }%",
                Toast.LENGTH_LONG).show()
            quizViewModel.correctAnswerCount = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.prevIndex = quizViewModel.currentIndex
            quizViewModel.moveToNext()
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
        updateQuestion()

        binding.prevButton.setOnClickListener {
            quizViewModel.currentIndex = quizViewModel.prevIndex
            updateQuestion()
        }
        binding.questionTextView.setOnClickListener {
            binding.nextButton.callOnClick()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        binding.cheatButton?.setOnClickListener {
            cheatActivityLouncher.launch(quizViewModel.currentQuestionAnswer)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX,quizViewModel.currentIndex)

    }
}

