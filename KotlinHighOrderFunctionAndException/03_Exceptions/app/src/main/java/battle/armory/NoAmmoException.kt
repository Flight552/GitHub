package battle.armory

class NoAmmoException : Exception() {
    override fun toString(): String {
        return "NoAmmoException()"
    }
}
