package battle.fight
import battle.army.Team

class Battle(team1Soldiers: Int = 1, team2Soldiers: Int = 1) {
    private var team1 = Team(team1Soldiers)
    private var team2 = Team(team2Soldiers)
    var battleIsOver: Boolean = false


    private fun battleState(): BattleState {
        var team1Health = 0
        var team2Health = 0
        for (i in team1.platoon) {
            team1Health += i.getHealth()
        }
        for (i in team2.platoon) {
            team2Health += i.getHealth()
        }
        //println("Team 1 health = $team1Health")
        // println("Team 2 health = $team2Health")
        when {
            team1Health > team2Health && team2Health <= 0 -> {
                battleIsOver = true
                return BattleState.Team1
            }
            team1Health < team2Health && team1Health <= 0 -> {
                battleIsOver = true

                return BattleState.Team2
            }
            team1Health == 0 && team2Health == 0 -> {
                battleIsOver = true
                return BattleState.Draw
            }
        }
        return BattleState.Progress(team1, team2)
    }

    fun inBattle() {
        while (!battleIsOver) {
            team1.platoon = team1.platoon.filter { !it.isKilled }.toMutableList() //новый лист с живыми воинами
            team2.platoon = team2.platoon.filter { !it.isKilled }.toMutableList()
            team1.platoon.shuffle()
            team2.platoon.shuffle()
            for ((index_1, i) in team1.platoon.withIndex()) {
                for ((index_2, j) in team2.platoon.withIndex()) {
                    i.attack(j)
                    if (!j.isKilled) {
                        j.attack(i)
                    } else {            //если убит, то ход переходит к следующему воину
                        if(team2.platoon.size > 1) {
                            team2.platoon[index_2 + 1].attack(i)
                        }
                    }
                    break
                }
                println(battleState().getResult())
                if (battleIsOver) {
                    break
                }
            }
        }
    }
}