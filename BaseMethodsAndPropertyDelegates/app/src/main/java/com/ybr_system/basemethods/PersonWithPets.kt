package com.ybr_system.basemethods

import kotlin.properties.ReadWriteProperty
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt
import kotlin.reflect.KProperty

private val listOfAnimals = listOf("Cat", "Dog", "Fish", "Hamster", "Bird", "Snake")

class PersonWithPets(
    private var height: Float,
    private var weight: Float,
    private var name: String,
    pets: HashSet<Animal> = hashSetOf()
) {

    var pets: HashSet<Animal> by ReadOnlyProperty(pets)

    fun buyPets() {
        pets.add(
            Animal(
                nextInt(10, 100),
                (nextFloat() * 100),
                listOfAnimals[nextInt(0, listOfAnimals.size)]
            )
        )
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonWithPets

        if (height != other.height) return false
        if (weight != other.weight) return false
        if (name != other.name) return false
        if (pets != other.pets) return false

        return true
    }

    override fun hashCode(): Int {
        var result = height.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (pets?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "PersonWithPets(name=$name)"
    }
}

class ReadOnlyProperty(
    private var pet: HashSet<Animal>
) : ReadWriteProperty<PersonWithPets, HashSet<Animal>> {

    override fun setValue(thisRef: PersonWithPets, property: KProperty<*>, value: HashSet<Animal>) {
        pet.addAll(value)
        println("${property.name}, ${thisRef.pets}")
    }

    override fun getValue(thisRef: PersonWithPets, property: KProperty<*>): HashSet<Animal> = pet
}
