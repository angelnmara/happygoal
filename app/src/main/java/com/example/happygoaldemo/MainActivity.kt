package com.example.happygoaldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.happygoaldemo.tools.BeeperControl
import com.example.happygoaldemo.tools.Tools
import com.example.happygoaldemo.ui.test.TestFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    val tools = Tools()
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration:AppBarConfiguration
    private lateinit var navViewEstadisticas: BottomNavigationView
    private lateinit var navViewTermometro: BottomNavigationView
    private lateinit var navViewComentariosDia: BottomNavigationView
    private lateinit var navigagionView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Tarea cron en kotlin
        //val beepControl = BeeperControl()
        //beepControl.beepForAnHour()

        navViewEstadisticas = findViewById(R.id.nav_view_bottom_estadistica)
        navViewTermometro = findViewById(R.id.nav_view_bottom_termometro)
        navViewComentariosDia = findViewById(R.id.nav_view_bottom_comentarios_dia)
        navigagionView = findViewById<NavigationView>(R.id.nav_view)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navigagionView.setupWithNavController(navController)
        navViewEstadisticas.setupWithNavController(navController)
        navViewTermometro.setupWithNavController(navController)
        navViewComentariosDia.setupWithNavController(navController)

        configureMenu()

        navigagionView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            when(it.itemId){
                R.id.nav_comentarios_del_dia->{
                    configureComentariosDia()
                    navController.navigate(R.id.comentarios_del_dia_nav_graph)
                    true
                }
                R.id.nav_cerrar_sesion->{
                    cerrarSesion()
                    true
                }
                R.id.estadisticaPersonalActivity->{
                    configureEstadisticas()
                    navController.navigate(R.id.estadistica_personal_nav_graph)
                    true
                }
                R.id.nav_termometro_general->{
                    configureTermometro()
                    navController.navigate(R.id.termometro_general_nav_graph)
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
        navViewEstadisticas.visibility= View.GONE
        navViewTermometro.visibility= View.GONE
        navViewComentariosDia.visibility = View.GONE
    }

    private fun configureEstadisticas(){
        /*appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.estadisticaPersonalListFragment, R.id.estadisticaPersonalChartFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navViewEstadisticas.visibility= View.VISIBLE
        navViewTermometro.visibility= View.GONE
        navViewComentariosDia.visibility = View.GONE
    }

    private fun configureTermometro(){
        /*appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.termometroGeneralFragment, R.id.termometroGeneralChartFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navViewTermometro.visibility= View.VISIBLE
        navViewEstadisticas.visibility= View.GONE
        navViewComentariosDia.visibility = View.GONE
    }

    private fun configureComentariosDia(){
        /*appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.comentariosDelDiaFragment, R.id.comentariosDelDiaChartFragment
            )
        )*/
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navViewComentariosDia.visibility = View.VISIBLE
        navViewTermometro.visibility= View.GONE
        navViewEstadisticas.visibility= View.GONE
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
