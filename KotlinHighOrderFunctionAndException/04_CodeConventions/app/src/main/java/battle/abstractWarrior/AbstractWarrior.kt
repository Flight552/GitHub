package battle.abstractWarrior

import battle.armory.AbstractWeapon
import kotlin.random.Random

abstract class AbstractWarrior(
    maxHealth: Int,
    override var evasion: Int,
    private val accuracy: Int,
    private val weapon: AbstractWeapon
) : Warrior {
    private var currentHealth = maxHealth

    override fun getHealth(): Int {
        return currentHealth
    }

    override fun attack(enemy: Warrior) {
        val fire = weapon.fireWeapon()
        var damage = 0
        if (!weapon.haveAmmo) {
            weapon.reload()
        }
        for (i in fire) {
            val random = Random.nextInt(0, 100)
            if (random > accuracy || random < enemy.evasion) {
                damage += i.giveDamage()
            }
        }
        enemy.damage(damage)
    }

    override fun damage(hit: Int) {
        currentHealth -= hit
        if (currentHealth < 0) {
            currentHealth = 0
            isKilled = true
        }
    }
}
