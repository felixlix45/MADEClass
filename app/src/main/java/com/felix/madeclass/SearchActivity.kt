package com.felix.madeclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar

class SearchActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val intent: Intent = getIntent()
        val query = intent.getStringExtra(extra_query)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        Toast.makeText(applicationContext, query.toString(), Toast.LENGTH_SHORT).show()
        setSupportActionBar(toolbar)
        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val svSearch: SearchView = findViewById(R.id.svSearch)
        svSearch.setQuery(query, false)

        svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query.toString(), Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    companion object{
       const val extra_query: String = "EXTRA_QUERY"
    }
}
