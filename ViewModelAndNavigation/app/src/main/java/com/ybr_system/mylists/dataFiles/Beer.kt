package com.ybr_system.mylists.dataFiles

sealed class Beer {
    data class Lager(
        val id: Long = 1,
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Porter(
        val id: Long = 2,
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Dunkel(
        val id: Long = 3,
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Stout(
        val id: Long = 4,
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Cider(
        val id: Long = 5,
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

}