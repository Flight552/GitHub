package com.example.criminalintent.data

import java.text.DateFormat
import java.util.*

data class Crime(
    var crimeId: UUID = UUID.randomUUID(),
    var crimeName: String = "",
    var crimeDate: Date = Date(),
    var crimeStatus: Boolean = false,
    var requiresPolice: Boolean = false
)