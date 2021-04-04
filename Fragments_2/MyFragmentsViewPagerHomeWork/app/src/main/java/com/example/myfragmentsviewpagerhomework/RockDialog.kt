package com.example.myfragmentsviewpagerhomework

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class RockDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listRock = arguments?.getStringArray(KEY_LIST_ROCK_GENRE)
        val listChecked  = TagsDialog.getArray(booleanArrayOf(true, true, true))
        return AlertDialog.Builder(context)
            .setTitle("Choose your poison!")
            .setNegativeButton("Cancel") { _, _ -> getToast(activity!!, "Canceled") }
            .setPositiveButton("Rock On!", DialogInterface.OnClickListener { _, position ->
                val isCheckedList = mutableListOf<String>()
                for ((index, value) in TagsDialog.rockMap) {
                    when (index) {
                        0 -> if (value) isCheckedList.add(RockGenre.ClassicalRock.name)
                        1 -> if (value) isCheckedList.add(RockGenre.Metal.name)
                        2 -> if (value) isCheckedList.add(RockGenre.TrashMetal.name)
                    }
                }
                ViewPagerFragment.getRockListFromDialog(isCheckedList)
                val fragment = fragmentManager?.findFragmentById(R.id.mainFragment)
                fragment?.let {
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.mainFragment, ViewPagerFragment())
                        ?.commit()
                }
                getToast(activity!!, "${isCheckedList}")
            })
            .setMultiChoiceItems(
                listRock,
                listChecked,
                DialogInterface.OnMultiChoiceClickListener { _, position, choice ->
                    TagsDialog.rockMap[position] = choice
                })
            .create()
    }

    companion object {
        private const val KEY_LIST_ROCK_GENRE = "rock list"

        fun getDialog(list: Array<String>): RockDialog {
            return RockDialog().createFragment {
                putStringArray(KEY_LIST_ROCK_GENRE, list)
            }
        }
    }

}
