package battle

import battle.fight.Battle

fun main() {
    println("Enter number of soldiers")
    val soldiers = readLine()?.toIntOrNull()
    if (soldiers != null) {
        val fight = Battle(soldiers, soldiers)
        fight.inBattle()
    } else {
        println("Invalid Number!!!")
    }
}
