package com.vicyrfahreza.githubuserapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicyrfahreza.githubuserapi.adapter.GithubUserAdapter
import com.vicyrfahreza.githubuserapi.data.User
import com.vicyrfahreza.githubuserapi.databinding.ActivityMainBinding
import com.vicyrfahreza.githubuserapi.model.MainViewModel
import com.vicyrfahreza.githubuserapi.model.SwitchThemeViewModel
import com.vicyrfahreza.githubuserapi.model.VMFSwitchTheme
import com.vicyrfahreza.githubuserapi.response.GithubResponseItem
import com.vicyrfahreza.githubuserapi.service.SettingPreferences
import com.vicyrfahreza.githubuserapi.service.SwitchThemeActivity

class MainActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_page -> {
                val favoriteIntent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(favoriteIntent)
            }
            R.id.theme_page -> {
                val themeIntent = Intent(this, SwitchThemeActivity::class.java)
                startActivity(themeIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, VMFSwitchTheme(pref)).get(
            SwitchThemeViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) {
            if(it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager =layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        binding.search.setOnClickListener {
            searchGithubUser()
        }

        viewModel.listGithubUser.observe(this) { githubUser ->
            setUserData(githubUser)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUserData(githubResponse: List<GithubResponseItem>){
        val listUser = ArrayList<User>()
        for(user in githubResponse){
            listUser.add(
                User(user.login, user.id, user.avatarUrl)
            )
        }
        val adapter = GithubUserAdapter(listUser)
        binding.rvUser.adapter = adapter
    }

    private fun searchGithubUser(){
        binding.apply {
            val searchData = search.text.toString()
            if(searchData.isEmpty()){
                return showLoading(true)
            }
            viewModel.setGithubUser(searchData)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}