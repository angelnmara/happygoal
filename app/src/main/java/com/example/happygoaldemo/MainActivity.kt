package com.example.happygoaldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.ui.test.TestFragmentDirections
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    val tools = Tools()
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var navView: BottomNavigationView
    private lateinit var navigagionView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigagionView = findViewById<NavigationView>(R.id.nav_view)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navView = findViewById(R.id.nav_view_bottom)
        navController = navHostFragment.navController
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navigagionView.setupWithNavController(navController)
        navView.setupWithNavController(navController)

        configureMenu()

        navigagionView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            when(it.itemId){
                R.id.nav_cerrar_sesion->{
                    cerrarSesion()
                    true
                }
                R.id.estadisticaPersonalActivity->{
                    configureEstadisticas()
                    navController.navigate(R.id.estadistica_personal_nav_graph)
                    true
                }
                else->{
                    configureMenu()
                    it.onNavDestinationSelected(navController)
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        configureMenu()
    }

    private fun configureMenu(){
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.visibility= View.GONE
    }

    private fun configureEstadisticas(){
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.estadisticaPersonalListFragment, R.id.estadisticaPersonalChartFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.visibility= View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    private fun cerrarSesion() {
        tools.savePreferences(this, getString(R.string.isloged), "false", 2)
        val action = TestFragmentDirections.actionTestFragmentSelf()
        navController.navigate(action)
    }

}
