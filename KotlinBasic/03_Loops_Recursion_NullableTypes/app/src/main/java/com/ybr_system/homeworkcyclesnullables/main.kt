package com.ybr_system.homeworkcyclesnullables

import kotlin.math.abs

fun main() {
    var sum = 0
    println("Enter number: ")
    readLine()?.toIntOrNull()
        ?.let {
            var counter = 0
            for (i in it downTo 1) {
                var digit = 1
                println("Enter any number: ")
                while (digit < it) {
                    println("this is it - $it")
                    val number = readLine()?.toIntOrNull() ?: continue
                    digit++
                    sum += number
                    if (number > 0) {
                        counter++
                    }
                }
            }
            println("You entered $counter positive numbers. Sum of all numbers = $sum")
        }
        ?: println("You did not enter a number")

    println("Enter another number")
    val getNumber = readLine()?.toIntOrNull()
        ?.let {
            println("Наибольший общий делитель для $it и $sum = ${denominator(it, sum)}")
        }
}

tailrec fun denominator(number1: Int, number2: Int): Int {
    if (number2 == 0) {
        return abs(number1)
    }
    return denominator(number2, number1 % number2)
}
