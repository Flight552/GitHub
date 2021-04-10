package com.example.criminalintent.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.criminalintent.data.Crime
import com.example.criminalintent.data.CrimeDao
import com.example.criminalintent.database.CrimeDatabase
import com.example.criminalintent.database.MIGRATION_1_2
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CrimeRepository private constructor(context: Context) {

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        CrimeDatabase.DATABASE_NAME
    ).addMigrations(MIGRATION_1_2)
        .build()

    private val crimeDao: CrimeDao = database.crimeDao()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimesList()
    fun getCrimeById(uuid: UUID): LiveData<Crime?> = crimeDao.getCrimeById(uuid)

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}