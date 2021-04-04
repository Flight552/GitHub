package com.ybr_system.mylists

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.ybr_system.mylists.dataFiles.Beer
import com.ybr_system.mylists.dataFiles.ItemDecoration
import com.ybr_system.mylists.adapters.AdapterBeer
import com.ybr_system.mylists.adapters.EndlessRecyclerViewScrollListener
import com.ybr_system.mylists.dataFiles.AutoClearedValue
import com.ybr_system.mylists.dataFiles.LayoutStyle
import com.ybr_system.mylists.dataFiles.beers
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator
import kotlinx.android.synthetic.main.fragment_activity.*
import kotlin.random.Random

class ListFragment(private val layout: String) : Fragment(R.layout.fragment_activity) {

    private var listOfBeers: MutableList<Beer> = beers
    private var beerAdapter by AutoClearedValue<AdapterBeer>()
    private var beerType = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            val fragment = this.fragmentManager
            fragment?.getFragment(savedInstanceState, "BEER")
        }
        initList()
        beerAdapter.updateBeers(listOfBeers)
        //  beerAdapter.notifyDataSetChanged()
        bt_floatingAddBeer.setOnClickListener {
            addBeerDialog()
        }
    }

    private fun initList() {
        beerAdapter = AdapterBeer { position ->
            deleteBeer(position)
        }

        with(rv_beer) {
            adapter = beerAdapter
            setHasFixedSize(true)
            var scrollView: RecyclerView.OnScrollListener? = null
            when (layout) {
                LayoutStyle.LINEAR.name -> {
                    addItemDecoration(dividerDecoration(DividerItemDecoration.HORIZONTAL))
                    addItemDecoration(dividerDecoration(DividerItemDecoration.VERTICAL))
                    layoutManager = LinearLayoutManager(requireContext())
                    scrollView = getScrollListener(LinearLayoutManager(requireContext()))
                }
                LayoutStyle.GRID.name -> {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                     scrollView = getScrollListener(GridLayoutManager(requireContext(), 2))
                }
                LayoutStyle.STAGGERED.name -> {
                    addItemDecoration(ItemDecoration(requireContext(), 10))
                    layoutManager =
                        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                     scrollView = getScrollListener(
                        StaggeredGridLayoutManager(
                            3,
                            StaggeredGridLayoutManager.VERTICAL
                        )
                    )
                }
            }
            if (scrollView != null) {
                addOnScrollListener(scrollView)
            }
            itemAnimator = ScaleInLeftAnimator()
        }
    }

    private fun addBeerDialog() {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_beer_fragment, null)
        val nameBeer = view.findViewById<EditText>(R.id.et_beerName_fragment)
        val countryBeer = view.findViewById<EditText>(R.id.et_beerCountry_fragment)
        val spinner = view.findViewById<Spinner>(R.id.spinner_beerType)
        spinnerItem(spinner)
        val dialog = AlertDialog.Builder(this.context)
            .setView(view)
            .setMessage("Add beer to your list")
            .setPositiveButton("Add", DialogInterface.OnClickListener { _, _ ->
                val name = nameBeer.text.toString()
                val country = countryBeer.text.toString()
                if (name.isNotEmpty() && country.isNotEmpty()) {
                    val idBeer = Random.nextLong()
                    getBeerFromDialog(id = idBeer, name = name, country = country, type = beerType)
                } else {
                    Toast.makeText(this.context, "Fields Should not be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(this.context, "Beer Canceled", Toast.LENGTH_SHORT).show()
            }
            .create()
        dialog.show()
    }

    private fun deleteBeer(position: Int) {
        listOfBeers.removeAt(position)
        beerAdapter.updateBeers(listOfBeers)
        // приходится оставлять нотификацию для корректного отображения списка
        beerAdapter.notifyItemRemoved(position)
        if (listOfBeers.isEmpty()) {
            Toast.makeText(context, "List is empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateBeerList(newBeer: Beer) {
        listOfBeers.add(0, newBeer)
        beerAdapter.updateBeers(listOfBeers)
        // приходится оставлять нотификацию для корректного отображения списка
        beerAdapter.notifyItemInserted(0)
    }

    private fun getBeerFromDialog(id: Long, name: String, country: String, type: String) {
        when (type) {
            "LAGER" -> {
                updateBeerList(
                    Beer.Lager(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            "PORTER" -> {
                updateBeerList(
                    Beer.Porter(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            "DUNKEL" -> {
                updateBeerList(
                    Beer.Dunkel(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            "STOUT" -> {
                updateBeerList(
                    Beer.Stout(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            "CIDER" -> {
                updateBeerList(
                    Beer.Cider(
                        id,
                        image = imageLager,
                        name = name,
                        type = type,
                        country = country
                    )
                )
            }
            else -> error("No such beer")
        }
    }

    private fun spinnerItem(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                beerType = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun dividerDecoration(orientation: Int): DividerItemDecoration {
        return DividerItemDecoration(requireContext(), orientation)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragment = fragmentManager?.findFragmentById(R.id.fragment_MainActivity)
        if (fragment != null) {
            fragmentManager?.putFragment(outState, "beer", fragment)
        }
    }

    private fun getScrollListener(manager: RecyclerView.LayoutManager): EndlessRecyclerViewScrollListener {
        return when (manager) {
            is LinearLayoutManager -> object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

                }
            }

            is GridLayoutManager -> object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

                }
            }
            is StaggeredGridLayoutManager -> object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

                }
            }
            else -> error("No such Layout")
        }
    }
}