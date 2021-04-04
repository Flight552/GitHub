package battle.abstractWarrior

interface Warrior {
    var isKilled: Boolean
    var evasion: Int
    fun attack(enemy: Warrior)
    fun damage(hit: Int)
    fun getHealth(): Int
}