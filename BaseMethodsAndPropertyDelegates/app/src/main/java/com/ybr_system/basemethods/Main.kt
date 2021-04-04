package com.ybr_system.basemethods

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
    //1.    Создайте класс Person со свойствами height (рост), weight (вес), name (имя).

    //2.    Создайте два объекта класса Person с полностью одинаковыми параметрами.
    val person1 = PersonNotEqual(184f, 76f, "Andrey")
    val person2 = PersonNotEqual(184f, 76f, "Andrey")
    //3.    Добавьте два объекта в HashSet.
    val personSetNotEqual = hashSetOf(person1, person2)
    //4.    Выведите количество итоговых элементов в HashSet.
    println("total elements in personSet = ${personSetNotEqual.size}")
    //5.    Сделайте так, чтобы в HashSet в итоге был один объект. Для этого реализуйте методы equals, hashcode. Для реализации методов воспользуйтесь автоматической генерацией кода в Android Studio. В этих методах должны учитываться все свойства класса Person: height, weight, name.
    val person3 = Person(184f, 76f, "Andrey")
    val person4 = Person(184f, 76f, "Andrey")
    val personSet = hashSetOf(person3, person4)
    println("total elements in personSet = ${personSet.size}")
    //6.    Добавьте ещё один объект Person в HashSet, отличающийся от первых двух.
    val person5 = Person(175f, 55f, "Masha")
    personSet.add(person5)
    println("total elements in personSet = ${personSet.size}")
    //7.    Выведите содержимое HashSet в консоль. Для этого реализуйте метод toString в классе Person. И с помощью forEach выведите всех пользователей c их свойствами в консоль.
    personSet.forEach {
        println(it.toString())
    }
    //8.    Создайте data class Animal с полями energy, weight, name.
    //9.    Добавьте в класс Person поле pets, которое хранит hashSet домашних животных типа Animal.
    //10.   Добавьте метод buyPet(), который создаёт объект класса Animal, проинициализированный случайными значениями полей, и добавляет его в pets.
    //11.    Перегенерируйте методы equals, hashcode, toString с учётом нового поля pets.
    //12.    С помощью делегированных свойств сделайте так, чтобы при добавлении домашнего животного в консоль печаталось имя персоны и её список домашних животных. Для этого используйте класс ReadOnlyProperty.
    //13.    Для каждой персоны из пункта 6 купите несколько домашних животных, проверьте, что в лог корректно выводится список домашних животных при обращении к полю pets.
    val personWithPets1 = PersonWithPets(184f, 67f, "Natasha")
    val personWithPets2 = PersonWithPets(184f, 67f, "Andrey")
    val personWithPets3 = PersonWithPets(184f, 67f, "Peter")
    val personsWithPetsList = hashSetOf(personWithPets1, personWithPets2, personWithPets3)
    personsWithPetsList.forEach {
        it.buyPets()
        it.buyPets()
        it.buyPets()
        it.buyPets()
    }

    println(personWithPets1.pets)
}

