package com.ybr_system.mylists

sealed class Beer {
    data class Lager(
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Porter(
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Dunkel(
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Stout(
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

    data class Cider(
        val image: String,
        val name: String,
        val type: String,
        val country: String
    ) : Beer()

}