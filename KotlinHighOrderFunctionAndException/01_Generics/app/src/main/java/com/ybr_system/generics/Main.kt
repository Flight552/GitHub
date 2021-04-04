package com.ybr_system.generics

import kotlin.math.round

fun main() {
    sortListByEvenNumbers(listOf(1, 4, 5, 8, 18))
    println("-----------------------------------")
    sortListByEvenNumbers(listOf(4, 2.43, 122.0, 122, 124.2, 34.0, -4342, 55, 0.99, -0.21))

    println("----------------------------------- Task 2 -----------------------------------")
    val q = Queue<String>()
    println(q.dequeue())
    q.enqueue("Hello")
    q.enqueue("World")
    println(q.dequeue())

    println("----------------------------------- Task 3 -----------------------------------")
    val success = Success<Int, String>(4)
    success.result(42.0, "Hello")
    val error = Error<Any, String>("World")
    error.result('c', "New World")
}

fun <T : Number> sortListByEvenNumbers(_elements: List<T>): List<T> {
    val newList: MutableList<T> = mutableListOf()
    for (x in _elements) {
        if (x == Long.MAX_VALUE) {
            continue
        }

        val l = x.toLong()
        if (l < Long.MAX_VALUE) {
            if (round(x.toDouble()) == x.toDouble() && l % 2 == 0L) {
                newList.add(x)
            }
        } else {
            newList.add(x)
        }
    }
    println("Четные числа: $newList")
    println("Вещественные числа $_elements")
    return newList
}



