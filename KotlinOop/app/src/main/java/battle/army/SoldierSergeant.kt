package battle.army

import battle.abstractWarrior.AbstractWarrior
import battle.armory.Weapon

class SoldierSergeant() :
    AbstractWarrior(maxHealth = 100, evasion = 15, accuracy = 60, weapon = Weapon.rifle) {
    override var isKilled: Boolean = false
}