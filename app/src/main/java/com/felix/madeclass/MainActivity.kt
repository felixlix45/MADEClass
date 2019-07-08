package com.felix.madeclass

import android.content.Intent
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        val fragment: Fragment
        when (menuItem.itemId) {
            R.id.title_movies -> {
                fragment = MoviesFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.title_tv_show -> {
                fragment = TvFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_language) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragment: Fragment
        fragment = MoviesFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName).commit()

    }


}
