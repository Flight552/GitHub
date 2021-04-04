package com.example.myfragmentsviewpagerhomework

object TagsDialog {

    val rockMap = mutableMapOf(0 to true, 1 to true, 2 to true)

    fun getArray(array: BooleanArray): BooleanArray {
        for((index, value) in rockMap) {
            array[index] = value
        }
        return array
    }
}