package com.template

import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.template.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.postsIndex.observe(this, this::updateTitle)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

//        var rootFile = File("/android_asset")
//        for(f in rootFile.listFiles()){
//            Log.d("TAG", "root: $f")
//            if(f.isDirectory){
//                for(d in f.listFiles()){
//                    Log.d("TAG", "root: $d")
//                }
//            }
//        }

        val parts = assets.list("content")
        mainActivityViewModel.postParts(parts)

        if (parts != null) {
            for(index in parts.indices){
                navView.menu.getItem(index).title = parts[index]
            }
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_main_menu, R.id.nav_f1, R.id.nav_f2,
                R.id.nav_f3, R.id.nav_f4, R.id.nav_f5, R.id.nav_exit
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    fun updateTitle(index: Int){
        Log.d("TAG", "updateTitle: $index")
        val parts = assets.list("content")
        if (parts != null) {
            binding.appBarMain.toolbar.title = parts[index]

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}