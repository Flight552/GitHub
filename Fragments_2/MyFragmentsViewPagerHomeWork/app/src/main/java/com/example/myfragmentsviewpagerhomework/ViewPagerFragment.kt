package com.example.myfragmentsviewpagerhomework

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.viewpager_layout.*
import kotlin.math.abs
import kotlin.random.Random

class ViewPagerFragment : Fragment(R.layout.viewpager_layout) {

    var rockBandCoversList: MutableList<RockBandObject> = mutableListOf(
        RockBandObject(
            R.string.black_sabbath_icon,
            R.drawable.blacksabbath_icon,
            RockGenre.TrashMetal.name
        ),
        RockBandObject(R.string.europe_icon, R.drawable.europe_icon, RockGenre.ClassicalRock.name),
        RockBandObject(
            R.string.pinkfloyd_icon,
            R.drawable.pinkfloyd_icon,
            RockGenre.ClassicalRock.name
        ),
        RockBandObject(R.string.metallica_icon, R.drawable.metallica_icon, RockGenre.Metal.name)
    )


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showDialog()
    }


    private fun showDialog() {
        val newList: MutableList<RockBandObject> = mutableListOf()
        if (rockList.isNotEmpty()) {
            rockBandCoversList.forEach {rockObject ->
                for (i in rockList) {
                    if (rockObject.genre == i) {
                        newList.add(rockObject)
                    }
                }
            }
        } else {
            newList.addAll(rockBandCoversList)
        }
        rockListAdapterSize = newList.size
        val myActivity = activity
        myActivity?.let {
            val wormDotsIndicator = view?.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
            val adapter: RockAdapter = RockAdapter(newList, myActivity)
            vp_FragmentActivity.adapter = adapter
            vp_FragmentActivity.offscreenPageLimit = 1
            wormDotsIndicator?.setViewPager2(vp_FragmentActivity)
            pageTransformation()
            tabMigration(newList)
            removeBadge()
            rockList.clear()
        }
    }

    private fun removeBadge() {
        vp_FragmentActivity.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab_FragmentActivity.getTabAt(position)?.removeBadge()
            }
        })
    }

    private fun tabMigration(list: List<RockBandObject>) {
        TabLayoutMediator(tab_FragmentActivity, vp_FragmentActivity) { tab, position ->
            val resourceID: String =
                getString(list[position].text).substringBefore(" ")
            tab.text = resourceID
        }.attach()
    }

    private fun pageTransformation() {
        vp_FragmentActivity.setPageTransformer { page, position ->
            page.translationX = -position * page.width;
            page.pivotX = 0f;
            page.pivotY = page.height / 2f;
            page.cameraDistance = 20000f;
            when {
                position < -1 || position > 1 -> {
                    page.alpha = 0f
                }
                position <= 0 -> {
                    page.alpha = 1f
                    page.rotationY = -120 * abs(position);
                }
                position <= 1 -> {
                    page.alpha = 1f
                    page.rotationY = 120 * abs(position);
                }
            }
        }
    }

    companion object {
        private val rockList = mutableListOf<String>()
        var rockListAdapterSize = 0
        fun getRockListFromDialog(_rockList: List<String>) {
            rockList.addAll(_rockList)
        }
    }
}