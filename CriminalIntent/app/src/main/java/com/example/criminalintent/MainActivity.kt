package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.criminalintent.fragments.CrimeFragment
import com.example.criminalintent.fragments.CrimeListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)

        managerTransactions(currentFragment, R.id.mainFragmentContainer)

    }

    private fun managerTransactions(currentFragment: Fragment?, container: Int) {
        if (currentFragment == null) {
            val fragment: CrimeListFragment = CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(container, fragment)
                .commit()
        }
    }
}
