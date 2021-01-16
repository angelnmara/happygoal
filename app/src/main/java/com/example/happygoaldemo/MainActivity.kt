package com.example.happygoaldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.ui.test.TestFragmentDirections
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    val tools = Tools()
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        var navigagionView = findViewById<NavigationView>(R.id.nav_view)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val layout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
        navigagionView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            when(it.itemId){
                R.id.nav_cerrar_sesion->{
                    cerrarSesion()
                    true
                }
                else->{
                    it.onNavDestinationSelected(navController)
                    true
                }
            }
        }
    }

    private fun cerrarSesion() {
        tools.savePreferences(this, getString(R.string.isloged), "false", 2)
        val action = TestFragmentDirections.actionTestFragmentSelf()
        navController.navigate(action)
    }

}
