package com.ybr_system.generics

fun main() {
    val getList = Queue<Int>()
    getList.enqueue(15)
    getList.enqueue(34)
    getList.enqueue(26)
    getList.enqueue(74)
    getList.enqueue(55)

    val newList = getList.filter { it % 2 == 0}
    println(newList.itemList)
}




