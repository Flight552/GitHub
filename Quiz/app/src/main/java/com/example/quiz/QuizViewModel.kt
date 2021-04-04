package com.example.quiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    private var previousIndex = 0
    private var counter = 0
    var isCheater: Boolean = false
    var cheatCounter: Int = 0

    private var rightAnswerCounter = 0F
    val totalResult: Float
        get() = ((rightAnswerCounter / questionBank.size) * 100)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].question

    fun incrementRightAnswer() {
        rightAnswerCounter++
    }


    fun getResult(): Boolean {
        return counter == questionBank.size - 1
    }

    fun moveToNext() {
        previousIndex = currentIndex
        currentIndex = (currentIndex + 1) % questionBank.size
        counter++
    }

    fun moveToPrevious() {
        currentIndex = previousIndex
        if (currentIndex > 0)
            currentIndex--
    }

}