package com.ybr_system.generics

class Queue<T>() {
    val itemList: MutableList<T> = mutableListOf()

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
    inline fun filter(predicate: (value: T) -> Boolean): Queue<T> {
        val newQueue = Queue<T>()
        for(index in 0 until itemList.size) {
            val element = predicate(itemList[index])
            if(element){
                newQueue.enqueue(itemList[index])
            }
        }
        return newQueue
    }
}

