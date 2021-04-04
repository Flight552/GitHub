package battle.armory

import kotlin.random.Random

enum class Ammo(private val damage: Int, private var criticalChance: Int, private var criticalDamage: Int) {
    NORMAL(5, 10, 5*3),
    BURST(25, 15, 25*3),
    PIERCED(15, 7, 15*3);

    fun giveDamage(): Int {
        val random = Random.nextInt(0, 100)
        return if (random > criticalChance) {
            criticalDamage
        } else {
            damage
        }
    }
}