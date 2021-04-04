package battle.army

import battle.abstractWarrior.AbstractWarrior
import battle.armory.Weapon

class SoldierPrivate() :
    AbstractWarrior(maxHealth = 50, evasion = 10, accuracy = 50, weapon = Weapon.pistol) {
    override var isKilled: Boolean = false
}
