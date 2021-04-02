package com.example.criminalintent.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.criminalintent.database.CrimeContracts
import java.util.*

@Entity(tableName = CrimeContracts.TABLE_NAME)
data class Crime(
    @PrimaryKey
    @ColumnInfo(name = CrimeContracts.Columns.CRIME_PRIMARY_KEY)
    var crimeId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = CrimeContracts.Columns.CRIME_TITLE)
    var crimeName: String = "",
    @ColumnInfo(name = CrimeContracts.Columns.CRIME_DATE)
    var crimeDate: Date = Date(),
    @ColumnInfo(name = CrimeContracts.Columns.CRIME_IS_SOLVED)
    var crimeStatus: Boolean = false,
    @ColumnInfo(name = CrimeContracts.Columns.CRIME_INVOLVE_POLICE)
    var requiresPolice: Boolean = false
)