package com.bignerdrunch.android.geoname

import androidx.lifecycle.ViewModel


class QuizViewModel: ViewModel() {
    private val questionBank = listOf(Question(R.string.question_australia,true),
                              Question(R.string.question_oceans,true),
                              Question(R.string.question_mideast,false),
                              Question(R.string.question_africa,false),
                              Question(R.string.question_americas,true),
                              Question(R.string.question_asia,true)
    )
    val questionBankSize = questionBank.size
    var prevIndex = 0
    var correctAnswerCount = 0
    var currentIndex = 0
    val currentQuestionAnswer: Boolean
    get() = questionBank[currentIndex].answer

    val currentQuestionText:Int
    get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}


