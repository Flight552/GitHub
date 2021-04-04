package battle.army

import battle.abstractWarrior.AbstractWarrior
import battle.armory.Weapon

class SoldierGeneral() :
    AbstractWarrior(maxHealth = 200, evasion = 30, accuracy = 80, weapon = Weapon.machineGun) {
    override var isKilled: Boolean = false
}