package com.ybr_system.basemethods

class Person(
    private var height: Float,
    private var weight: Float,
    private var name: String
) {

    override fun toString(): String {
        return "name: $name, height: $height, weight: $weight"
    }

    override fun hashCode(): Int {
        var result = height.hashCode()
        result = 42 * result + weight.hashCode()
        result = 42 * result + name.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (height != other.height) return false
        if (weight != other.weight) return false
        if (name != other.name) return false

        return true
    }

}