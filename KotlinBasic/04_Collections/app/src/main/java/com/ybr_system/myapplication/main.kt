package com.ybr_system.myapplication

fun main() {
    println("Enter number of users:")
    readLine()?.toIntOrNull()
        ?.let {number ->
            val listOfNumbers = getUserNumber(number)
            val listOfFilteredNumbers = listOfNumbers.filter {it.startsWith("+7")}
            print("Phone numbers start with +7: ")
            for (i in listOfFilteredNumbers) {
                print("$i ")
            }
            println()
            val setOfUniqueNumbers = listOfFilteredNumbers.toSet()
            println("Total unique numbers: ${setOfUniqueNumbers.size}")
            println("Total length of all numbers = ${listOfNumbers.sumBy { it.length }}")

            val phoneBook: MutableMap<String, String> = emptyMap<String, String>().toMutableMap()
            for(i in listOfNumbers) {
                print("Введите имя человека с номером телефона $i: ")
                phoneBook[i] = readLine().toString()
            }
            for(i in phoneBook) {
                println("Человек: ${i.key}, Номер телефона: ${i.value}")
            }
        } ?: println("Not a correct number")
}

fun getUserNumber(users: Int): MutableList<String> {
    val listOfNumbers: MutableList<String> = mutableListOf()
    for (i in 1..users) {
        println("Enter user $i phone number")
        listOfNumbers.add(readLine().toString())
    }
    return listOfNumbers
}