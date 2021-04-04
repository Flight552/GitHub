package com.example.myfragmentsviewpagerhomework

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AlertDialogLayout
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import kotlinx.android.synthetic.main.fragment_rock.*
import kotlinx.android.synthetic.main.viewpager_layout.*
import kotlin.random.Random


class RockFragment : Fragment(R.layout.fragment_rock) {

    private val rockGenreList: Array<String> =
        arrayOf(RockGenre.ClassicalRock.name, RockGenre.Metal.name, RockGenre.TrashMetal.name)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_fragment.setText(requireArguments().getInt(KEY_TEXT_WebFragment))
        iv_fragment.setImageResource(requireArguments().getInt(KEY_IMAGE_WebFragment))

        bt_generateBadge.setOnClickListener {
            val size = ViewPagerFragment.rockListAdapterSize
            val getRandom = Random.nextInt(0, size)
            activity?.tab_FragmentActivity?.getTabAt(getRandom)?.orCreateBadge?.apply {
                if (number != 0) {
                    number++
                } else {
                    number = 1
                }
                badgeGravity = BadgeDrawable.BOTTOM_START
            }
        }

        activity?.vp_Toolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_button -> {
                    showDialog()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun showDialog() {
        val dialog = RockDialog.getDialog(rockGenreList)
        fragmentManager?.let { it -> dialog.show(it, "dialog") }
    }

    companion object {
        private const val KEY_IMAGE_WebFragment = "image key"
        private const val KEY_TEXT_WebFragment = "text key"

        fun rockFragmentInstance(
            @StringRes text: Int,
            @DrawableRes image: Int
        ): RockFragment {
            return RockFragment().createFragment {
                putInt(KEY_TEXT_WebFragment, text)
                putInt(KEY_IMAGE_WebFragment, image)
            }
        }
    }
}