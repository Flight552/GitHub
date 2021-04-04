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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.add_beer_fragment.*
import kotlinx.android.synthetic.main.fragment_activity.*

class ListFragment : Fragment(R.layout.fragment_activity) {
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
            fragment?.getFragment(savedInstanceState, "beer")
        }
        initList()
        beerAdapter?.updateBeers(listOfBeers)
        beerAdapter?.notifyDataSetChanged()
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
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
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
                    getBeerFromDialog(name, country, beerType)
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
        beerAdapter.notifyItemRemoved(position)
        if (listOfBeers.isEmpty()) {
            Toast.makeText(context, "List is empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateBeerList(newBeer: Beer) {
        listOfBeers.add(0, newBeer)
        beerAdapter.updateBeers(listOfBeers)
        beerAdapter.notifyItemInserted(0)
    }

    private fun getBeerFromDialog(name: String, country: String, type: String) {
        when (type) {
            "LAGER" -> {
                updateBeerList(Beer.Lager(imageLager, name, type, country))
            }
            "PORTER" -> {
                updateBeerList(Beer.Porter(imagePorter, name, type, country))
            }
            "DUNKEL" -> {
                updateBeerList(Beer.Dunkel(imageDunkel, name, type, country))
            }
            "STOUT" -> {
                updateBeerList(Beer.Stout(imageStout, name, type, country))
            }
            "CIDER" -> {
                updateBeerList(Beer.Cider(imageCider, name, type, country))
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragment = fragmentManager?.findFragmentById(R.id.fragment_MainActivity)
        if (fragment != null) {
            fragmentManager?.putFragment(outState, "beer", fragment)
        }
    }

}