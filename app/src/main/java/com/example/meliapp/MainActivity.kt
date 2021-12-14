package com.example.meliapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.meliapp.databinding.ActivityMainBinding
import com.example.meliapp.view.fragment.ProductDetailPageFragment
import com.example.meliapp.view.fragment.SearchProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        binding.searchView.isGone = destination.label == ProductDetailPageFragment::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupSearchView()
        getNavigationController()
        configureActionBarWithNavController()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.searchToolbar)
    }

    private fun setupSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    }

    private fun getNavigationController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun configureActionBarWithNavController() {
        setUpAppBarConfiguration()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setUpAppBarConfiguration() {
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setSearchToFragment(intent)
    }

    private fun setSearchToFragment(intent: Intent?) {
        val currentFragment = getCurrentFragment()

        if (Intent.ACTION_SEARCH == intent?.action && currentFragment is SearchProduct) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            (currentFragment as SearchProduct).onNewSearch(query.orEmpty())
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        return navHostFragment?.childFragmentManager?.fragments?.first()
    }
}