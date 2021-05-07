package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminalintent.data.Crime
import com.example.criminalintent.data.CrimeDao

@Database(entities = [Crime::class], version = CrimeDatabase.DATABASE_VERSION)
@TypeConverters(CrimeTypeConverter::class)
abstract class CrimeDatabase: RoomDatabase() {

    abstract fun crimeDao(): CrimeDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "crime-database"
    }
}