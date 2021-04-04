package com.skillbox.multithreading

import android.util.Log
import androidx.fragment.app.Fragment

class DeadlockFragment : Fragment() {

    override fun onResume() {
        super.onResume()

//        val friend1 = Person("Вася")
//        val friend2 = Person("Петя")
//        val thread1 = Thread {
//            friend1.throwBallTo(friend2)
//        }
//        val thread2 = Thread {
//            friend2.throwBallTo(friend1)
//        }
//        thread1.start()
//        thread2.start()

        val number1 = Increment(10)
        val number2 = Increment(20)

//        val threadNumber1 = Thread {
//            number1.deadLockIncrement(number2)
//        }.apply { start() }
//        val threadNumber2 = Thread {
//            number2.deadLockIncrement(number1)
//        }.apply { start() }

        val threadNumber1 = Thread {
            number1.increment(number2)
        }
        val threadNumber2 = Thread {
            number2.increment(number1)
        }

        threadNumber1.start()
        threadNumber2.start()
    }


    data class Person(
        val name: String
    ) {

        fun throwBallTo(friend: Person) {
            synchronized(this) {
                Log.d(
                    "Person",
                    "$name бросает мяч ${friend.name} на потоке ${Thread.currentThread().name}"
                )
                Thread.sleep(500)
            }
            friend.throwBallTo(this)
        }

    }

    data class Increment(var number: Int) {

        fun increment(anotherNumber: Increment) {
          synchronized(this) {
              number++
              Log.d(
                  "First number",
                  "Number incrementation $number на потоке ${Thread.currentThread().name}"
              )
              Thread.sleep(1000)
          }
            anotherNumber.increment(this)
        }

        fun deadLockIncrement(anotherNumber: Increment) {
            synchronized(this) {
                number++
                Log.d(
                    "First number",
                    "Number incrementation $number на потоке ${Thread.currentThread().name}"
                )
                Thread.sleep(1000)
                anotherNumber.deadLockIncrement(this)
            }
        }
    }
}