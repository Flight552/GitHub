package com.ybr_system.mylists

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.*
import com.ybr_system.mylists.adapters.AdapterBeer
import com.ybr_system.mylists.adapters.EndlessRecyclerViewScrollListener
import com.ybr_system.mylists.dataFiles.*
import com.ybr_system.mylists.viewModelFiles.BeerViewModel
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator
import kotlinx.android.synthetic.main.fragment_activity.*
import kotlin.random.Random

class ListFragment : Fragment(R.layout.fragment_activity) {

    private val beerViewModel: BeerViewModel by viewModels()
    private var beerAdapter by AutoClearedValue<AdapterBeer>()
    private var beerType = BeerTypes.LAGER.name
    private val args: ListFragmentArgs by navArgs()
    private var layout: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layout = args.layoutFragment
        initList()
        beerAdapter.updateBeers(beerViewModel.getBeerList())
        beerAdapter.notifyDataSetChanged()
        observeOnBeerChange()
        bt_floatingAddBeer.setOnClickListener {
            addBeerDialog()
        }
    }

    private fun initList() {

        beerAdapter = AdapterBeer({ position ->
            val action = ListFragmentDirections
                .actionListFragmentToDetailedFragment(getIdFromModel(position))
            findNavController()
                .navigate(action)
        }, { position ->
            deleteBeer(position)
        }
        )

        with(rv_beer) {
            adapter = beerAdapter
            setHasFixedSize(true)
            chooseLayout(rv_beer)
        }
    }

    private fun chooseLayout(rv: RecyclerView) {
        with(rv) {
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

    private fun deleteBeer(position: Int) {
        beerViewModel.deleteBeerByPosition(position)
        beerAdapter.updateBeers(beerViewModel.getBeerList())
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
                    beerAdapter.notifyItemChanged(0)
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

    private fun getBeerFromDialog(id: Long, name: String, country: String, type: String) {
        beerViewModel.beerDialog(id, name, country, type)
    }

    private fun getIdFromModel(position: Int): Long {
        return beerViewModel.onClickBeerItem(position)
    }

    private fun observeOnBeerChange() {
        beerViewModel.beerList
            .observe(viewLifecycleOwner) {
                beerAdapter.updateBeers(beerViewModel.getBeerList())
            }
        beerViewModel.showToastBeerLiveData
            .observe(viewLifecycleOwner) {text ->
                beerViewModel.showToastMessage(requireContext(), text)
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