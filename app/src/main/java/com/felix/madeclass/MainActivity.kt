package com.felix.madeclass

import android.content.Intent
import android.provider.Settings
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    lateinit var coordinatorLayout: CoordinatorLayout
    private var doubleBackToExitPressedOnce = false

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        var selectedFragment: Fragment? = null

        when (menuItem.itemId) {
            R.id.title_movies -> {
                selectedFragment = MoviesFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment, selectedFragment.javaClass.simpleName).commit()
            }
            R.id.title_tv_show -> {
                selectedFragment = TvFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment, selectedFragment.javaClass.simpleName).commit()
            }
        }

//        val currFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
//
//        if(selectedFragment!!.isHidden){
//            supportFragmentManager.beginTransaction().show(selectedFragment).commit()
//        }else{
//            supportFragmentManager.beginTransaction().add(R.id.fragment_container, selectedFragment).hide(currFragment!!).commit()
//        }
        true
    }

    override fun onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to leave", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
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

        coordinatorLayout = findViewById(R.id.coordinatorLayout)

        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragment: Fragment
        fragment = MoviesFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName).commit()

    }


}
