package com.bignerdrunch.android.geoname

import android.app.ActivityOptions
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.bignerdrunch.android.geoname.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    // creating a link between QuizViewModel and MainActivity
    private val quizViewModel:QuizViewModel by lazy{
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    // declare binding
    private lateinit var binding:ActivityMainBinding

    private val cheatActivityLauncher = registerForActivityResult(CheatActivity.Contract()){
        if (it){
            quizViewModel.answersMap[quizViewModel.currentQuestionText] = it
        }
        isQuestionWasAnswered()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initial binding
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            quizViewModel.answersMap.put(quizViewModel.currentQuestionText,true)
            disablingButtons()
        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
            quizViewModel.answersMap.put(quizViewModel.currentQuestionText,true)
            disablingButtons()
        }

        binding.previousButton.setOnClickListener{
            if (quizViewModel.previousIndex < 0){
                quizViewModel.previousIndex = -1
                Toast.makeText(this,R.string.previous_button_problem,Toast.LENGTH_SHORT).show()
            } else {
                quizViewModel.currentIndex = quizViewModel.previousIndex
                quizViewModel.previousIndex--
                updateQuestion()
                isQuestionWasAnswered()
            }
        }

        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            isQuestionWasAnswered()
        }

        binding.cheatButton.setOnClickListener{ view->
            val options : ActivityOptionsCompat =
                ActivityOptionsCompat.makeClipRevealAnimation(view,0,0,view.width,view.height)
            cheatActivityLauncher.launch(quizViewModel.currentQuestionAnswer,options)
        }

        updateQuestion()
        isQuestionWasAnswered()
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) quizViewModel.correctQuestionCount++
        val message = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else{
            R.string.incorrect_toast
        }
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        getResult()
    }

    private fun getResult(){
        if (quizViewModel.currentIndex == quizViewModel.questionSize - 1){
            Toast.makeText(this,"${
                (quizViewModel.correctQuestionCount.toDouble() / quizViewModel.questionSize.toDouble()) * 100 }%",Toast.LENGTH_LONG
            ).show()

            quizViewModel.correctQuestionCount = 0
        }
    }


    private fun updateQuestion(){
        binding.questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun disablingButtons(){
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    private fun enablingButtons(){
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }

    private fun isQuestionWasAnswered(){
        if (quizViewModel.answersMap[quizViewModel.currentQuestionText] == true){
            disablingButtons()
        } else{
            enablingButtons()
        }
    }
}

