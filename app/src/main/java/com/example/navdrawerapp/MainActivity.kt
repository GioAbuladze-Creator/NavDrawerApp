package com.example.navdrawerapp

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var frameLayout: FrameLayout
    private lateinit var navView:NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        frameLayout = findViewById(R.id.frame_layout_1)
        navView=findViewById(R.id.nav_view )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.setOnApplyWindowInsetsListener { _, insets ->
                // Get the safe area insets (including the notch) from the WindowInsets
                val topInset = insets.getInsets(WindowInsets.Type.systemBars()).top

                // Apply padding to the FrameLayout
                frameLayout.setPadding(0, topInset, 0, 0)
                navView.setPadding(0,topInset,0,0)
                // Return the insets to let the system know that we've consumed them
                insets
            }
        }
        drawerLayout=findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.home ->{
                    replaceFragment(HomeFragment(),it.title.toString())
                }
                R.id.message ->{
                    replaceFragment(MessageFragment(),it.title.toString())
                }
                R.id.settings ->{
                    replaceFragment(SettingsFragment(),it.title.toString())
                }
                R.id.login ->{
                    replaceFragment(LoginFragment(),it.title.toString())
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment, title:String){
        val fragmentManager=supportFragmentManager
        val fragTrans= fragmentManager.beginTransaction()
        fragTrans.replace(R.id.frame_layout_1,fragment)
        fragTrans.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}