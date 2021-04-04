package battle.fight

import battle.abstractWarrior.AbstractWarrior
import battle.army.Team

sealed class BattleState {
    class Progress(private val team1: Team, private val team2: Team): BattleState() {
        override fun toString(): String {
            return """Progress. Team 1 health: ${team1.platoon.sumBy { (it as AbstractWarrior).getHealth() }},
            Team 2 health: ${team2.platoon.sumBy { (it as AbstractWarrior).getHealth() }}"""
        }
    }

    object Team1 : BattleState() {
        override fun toString(): String {
            return "Team 1 wins"
        }
    }

    object Team2 : BattleState() {
        override fun toString(): String {
            return "Team 2 wins"
        }
    }

    object Draw : BattleState() {
        override fun toString(): String {
            return "Draw"
        }
    }

    fun getResult(): String {
        return this.toString()
    }

}