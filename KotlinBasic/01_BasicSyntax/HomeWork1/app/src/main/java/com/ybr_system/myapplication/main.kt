package com.ybr_system.myapplication

fun main() {
    val firstName = "Andrey"
    val lastName = "Vas"
    var height = 184
    var weight = 75f
    var isChild: Boolean =  (height < 150 && weight < 40f)
    var info = "Name: $firstName, lastName: $lastName, height: $height, weight: $weight, " +
            "is a child: $isChild}"
    println(info)
    height = 149
    weight = 39f
    isChild = (height < 150 && weight < 40f)
    info = "Name: $firstName, lastName: $lastName, height: $height, weight: $weight, " +
            "is a child: $isChild"
    println(info)

}