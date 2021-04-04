package battle.armory

sealed class FireType(val shot: Int) {
    object SingleMode: FireType(1)
    object BurstMode: FireType(3)
}