package com.bignerdrunch.android.geoname

import androidx.lifecycle.ViewModel


class QuizViewModel: ViewModel() {
    private val questionBank = listOf(Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true)
    )

    var answersMap = mutableMapOf<Int,Boolean>()

    val questionSize = questionBank.size
    var previousIndex = -1
    var currentIndex = 0
    var correctQuestionCount = 0

    val currentQuestionAnswer:Boolean
        get() =questionBank[currentIndex].answer

    val currentQuestionText:Int
        get() = questionBank[currentIndex].question

    fun moveToNext(){
        if (currentIndex < questionSize - 1){
            previousIndex = currentIndex
            currentIndex++
        } else {
            currentIndex = 0
            previousIndex = -1
            answersMap.clear()
        }
    }


}


