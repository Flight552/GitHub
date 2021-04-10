package com.example.criminalintent.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.criminalintent.database.CrimeContracts
import java.util.*

@Dao
interface CrimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrime(list: List<Crime>)

    @Query("SELECT * FROM ${CrimeContracts.TABLE_NAME}")
    fun getCrimesList() : LiveData<List<Crime>>

    @Query("SELECT * FROM ${CrimeContracts.TABLE_NAME} WHERE ${CrimeContracts.Columns.CRIME_PRIMARY_KEY} = (:id)")
    fun getCrimeById(id: UUID): LiveData<Crime?>

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)
}