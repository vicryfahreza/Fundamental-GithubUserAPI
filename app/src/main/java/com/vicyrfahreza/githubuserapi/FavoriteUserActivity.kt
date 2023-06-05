package com.vicyrfahreza.githubuserapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicyrfahreza.githubuserapi.adapter.FavoriteUserAdapter
import com.vicyrfahreza.githubuserapi.database.FavoriteUser
import com.vicyrfahreza.githubuserapi.databinding.ActivityFavoriteUserBinding
import com.vicyrfahreza.githubuserapi.model.FavoriteUserViewModel
import com.vicyrfahreza.githubuserapi.model.ViewModelFactory
import com.vicyrfahreza.githubuserapi.response.FavoriteUserResponse

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private val viewModel by viewModels<FavoriteUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager =layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        viewModel.getAllFavoriteUser().observe(this) { fav: List<FavoriteUser> ->
            val items = arrayListOf<FavoriteUserResponse>()
            fav.map {
                val item = FavoriteUserResponse(login = it.username, avatarUrl = it.avatarUrl.toString())
                items.add(item)
            }
            binding.rvUser.adapter = FavoriteUserAdapter(items)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}