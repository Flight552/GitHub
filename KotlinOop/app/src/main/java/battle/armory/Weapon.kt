package battle.armory

object Weapon {
    val pistol = object : AbstractWeapon(maxAmmo = 20, fireType = FireType.SingleMode) {
        override fun getAmmo() = Ammo.NORMAL
    }

    val rifle = object : AbstractWeapon(maxAmmo = 15, fireType = FireType.SingleMode) {
        override fun getAmmo() = Ammo.PIERCED
    }

    val machineGun = object : AbstractWeapon(maxAmmo = 50, fireType = FireType.BurstMode) {
        override fun getAmmo()= Ammo.BURST

    }

    val shotGun = object : AbstractWeapon(maxAmmo = 8, fireType = FireType.SingleMode) {
        override fun getAmmo() = Ammo.BURST
    }

}