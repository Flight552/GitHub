package com.ybr_system.generics

class Queue<T>() {
        private val itemList: MutableList<T> = mutableListOf()

    fun enqueue(item: T) {
        itemList.add(item)
    }

   fun dequeue(): T? {
       return if(itemList.isEmpty()) {
           null
       } else {
           itemList.removeAt(0)
       }
   }
}