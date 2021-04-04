package com.example.mytoolbar

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag: String = "MyActivityLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.getLogs(tag, "OnCreate")
        chooseSong()
        searchSomething(this)
        getError()
    }

    override fun onStart() {
        super.onStart()
        Logger.getLogs(tag, "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Logger.getLogs(tag, "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Logger.getLogs(tag, "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.getLogs(tag, "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.getLogs(tag, "OnDestroy")
    }

    private fun getError() {
        bt_Error.setOnClickListener {
            Thread.sleep(10000)
        }
    }

    private fun chooseSong() {
        toolbar_main.setOnMenuItemClickListener {item ->
            when(item.itemId) {
                R.id.item_song1 -> {
                    val text = getText(R.string.tv_song1)
                    tv_TextLong.text = text
                    true
                }
                R.id.item_song2 -> {
                    val text = getText(R.string.tv_song2)
                    tv_TextLong.text = text
                    true
                }
                else -> {
                    val text = getText(R.string.tv_theSongBook)
                    tv_TextLong.text = text
                    true
                }
            }
        }
    }


    private fun searchSomething(context: Context) {
        val searchButton = toolbar_main.menu.findItem(R.id.item_search)
        searchButton.setOnActionExpandListener(object: MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Toast.makeText(context, "Search in progress", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Toast.makeText(context, "Search finished", Toast.LENGTH_SHORT).show()
                return true
            }

        })
        (searchButton.actionView as androidx.appcompat.widget.SearchView).
                setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val text = query ?: "Nothing is Entered"
                        tv_TextLong.text = text
                        return true

                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }

                })

        toolbar_main.setNavigationOnClickListener {
            Toast.makeText(this, R.string.toast_arrow, Toast.LENGTH_LONG).show()
        }
    }
}

object Logger {
    fun getLogs(tag: String, message: String) {
        if(BuildConfig.DEBUG) {
            Log.d(tag, message)
            Log.e(tag, message)
            Log.i(tag, message)
            Log.v(tag, message)
            Log.w(tag, message)
        }
    }
}