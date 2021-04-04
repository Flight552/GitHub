package com.example.quiz

import androidx.annotation.StringRes

data class Question(
    @StringRes val question: Int,
     val answer: Boolean
)