package battle.army

import battle.abstractWarrior.Warrior
import kotlin.random.Random

class Team(soldiers: Int = 1) {
    var platoon: MutableList<Warrior> = addSoldiers(soldiers)
    private fun addSoldiers(soldiers: Int): MutableList<Warrior> {
        val createTeam = mutableListOf<Warrior>()
        for (i in 1..soldiers) {
            when (Random.nextInt(0, 101)) {
                in 0..50 -> createTeam.add(SoldierPrivate())
                in 51..85 -> createTeam.add(SoldierSergeant())
                in 86..95 -> createTeam.add(SoldierColonel())
                in 96..100 -> createTeam.add(SoldierGeneral())
            }
        }
        return createTeam
    }
}
