package com.example.criminalintent.database

object CrimeContracts {
    const val TABLE_NAME = "Crime"

    object Columns {
        const val CRIME_PRIMARY_KEY = "id"
        const val CRIME_TITLE = "title"
        const val CRIME_DATE = "date"
        const val CRIME_IS_SOLVED = "isSolved"
        const val CRIME_INVOLVE_POLICE = "police"
    }
}