package battle.armory

abstract class AbstractWeapon(private var maxAmmo: Int, private val fireType: FireType) {
    // list of bullets
    var listAmmo: MutableList<Ammo> = mutableListOf()

    // check is the clip is empty
    var haveAmmo: Boolean = false
        private set

    // get bullets
    abstract fun getAmmo(): Ammo

    // reload weapon
    fun reload() {
        val listBullets = mutableListOf<Ammo>()
        for (i in 0 until maxAmmo) {
            listBullets.add(getAmmo())
        }
        haveAmmo = true
        listAmmo = listBullets
    }

    //fire weapon
    fun fireWeapon(): List<Ammo> {
        val bullets = mutableListOf<Ammo>()
        if (haveAmmo) {
            for (i in 0 until fireType.shot) {
                if (listAmmo.isEmpty()) {
                    haveAmmo = false
                    break
                }
                bullets.add(listAmmo.removeAt(0))
            }
        }
        return bullets
    }
}