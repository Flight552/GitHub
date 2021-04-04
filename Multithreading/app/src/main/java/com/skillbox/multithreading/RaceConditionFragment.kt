package com.skillbox.multithreading

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.racecondition_layout.*

class RaceConditionFragment : Fragment(R.layout.racecondition_layout) {

    private var value: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bt_not_sync.setOnClickListener {
            onNotSync()
        }

        bt_sync.setOnClickListener {
            onSync()
        }

        bt_threads.setOnClickListener {
            clearEditText()
            getMinimumThread()
        }

    }

    override fun onResume() {
        super.onResume()
        makeMultithreadingIIncrement()
    }

    private fun onNotSync() {
        val array = getUserNumbers()
        val expectedValue = array[0] * array[1]
        val currentTime = System.currentTimeMillis()
        (0 until array[0]).map {
            Thread {
                for (i in 0 until array[1]) {
                    value++
                }
            }.apply {
                start()
            }
        }.map { it.join() }
        val timeFinish = System.currentTimeMillis() - currentTime
        val result = """
            Expected: $expectedValue 
            Current: $value
            Time: $timeFinish
            """.trimIndent()
        tv_result.text = result
    }

    private fun onSync() {
        val array = getUserNumbers()
        val expectedValue = array[0] * array[1]
        val currentTime = System.currentTimeMillis()
        (0 until array[0]).map {
            Thread {
                synchronized(this) {
                    for (i in 0 until array[1]) {
                        value++
                    }
                }
            }.apply {
                start()
            }
        }.map { it.join() }
        val timeFinish = System.currentTimeMillis() - currentTime
        val result = """
            Expected: $expectedValue 
            Current: $value
            Time: $timeFinish
            """.trimIndent()
        tv_result.text = result
    }

    private fun clearEditText() {
        if (et_numberM.text.isNotBlank())
            et_numberM.text.clear()
        if (et_threads.text.isNotBlank())
            et_threads.text.clear()
    }

    private fun getUserNumbers(): Array<Int> {
        value = 0
        val threadCount = et_threads.text.toString()
        val incrementCount = et_numberM.text.toString()
        return if (threadCount.isNotBlank() || incrementCount.isNotBlank()) {
            arrayOf(threadCount.toInt(), incrementCount.toInt())
        } else {
            arrayOf(0, 0)
        }
    }

    private fun getMinimumThread() {
        var isTrue = true
        val numberThreads = 2
        var numberIncrement = 0
        var currentValue = 0
        var expectedValue = 0
        val currentTime = System.currentTimeMillis()

        while (isTrue) {
            expectedValue = numberIncrement * numberThreads
            currentValue = 0
            (0 until 2).map {
                Thread {
                    for (i in 0 until numberIncrement) {
                        currentValue++
                    }
                }.apply {
                    start()
                }
            }.map { it.join() }
            if (currentValue != expectedValue) {
                isTrue = false
            }
            numberIncrement++
        }
        val timeFinish = currentTime - System.currentTimeMillis()
        val result = """
            Expected: $expectedValue 
            Current: $currentValue
            Time: $timeFinish
            Incrementation: $numberIncrement
            """.trimIndent()
        tv_result.text = result

    }

    private fun makeMultithreadingIIncrement() {
        val threadCount = 100
        val incrementCount = 1000000
        val expectedValue = value + threadCount * incrementCount

        (0 until threadCount).map {
            Thread {
                synchronized(this) {
                    for (i in 0 until incrementCount) {
                        value++
                    }
                }
            }.apply {
                start()
            }
        }
            .map { it.join() }
        Toast.makeText(requireContext(), "value=$value, expected=$expectedValue", Toast.LENGTH_LONG)
            .show()
    }

}