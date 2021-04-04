package battle.army

import battle.abstractWarrior.AbstractWarrior
import battle.armory.Weapon

class SoldierColonel() :
    AbstractWarrior(maxHealth = 150, evasion = 25, accuracy = 70, weapon = Weapon.shotGun) {
    override var isKilled: Boolean = false
}
