package com.vicyrfahreza.githubuserapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.vicyrfahreza.githubuserapi.adapter.SectionsPagerAdapter
import com.vicyrfahreza.githubuserapi.database.FavoriteUser
import com.vicyrfahreza.githubuserapi.databinding.ActivityDetailUserBinding
import com.vicyrfahreza.githubuserapi.model.DetailUserViewModel
import com.vicyrfahreza.githubuserapi.model.ViewModelFactory
import kotlin.properties.Delegates

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel by viewModels<DetailUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatar = intent.getStringExtra(EXTRA_AVATAR_URL)

        val favoriteUser = FavoriteUser(username.toString(), avatar)

        var status = false

        viewModel.getFavoriteUser(username.toString()).observe(this) {
            if(it != null){
                status = true
                binding.fab.setBackgroundDrawable(ContextCompat.getDrawable(binding.fab.context, R.drawable.ic_favorite))
            } else {
                status = false
                binding.fab.setBackgroundDrawable(ContextCompat.getDrawable(binding.fab.context, R.drawable.ic_favorite_border))
            }
        }

        binding.fab.setOnClickListener {
            if(!status){
                viewModel.insert(favoriteUser)
            }else {
                viewModel.delete(favoriteUser)
            }
        }

        viewModel.setGithubUser(username.toString())

        viewModel.getGithubUser().observe(this) {
            binding.apply {
                tvGithubUsername.text = it.name
                tvGithubName.text = it.login
                tvGithubFollowers.text ="${it.followers} Followers"
                tvGithubFollowing.text ="${it.following} Following"
                if(it.bio != null) tvGithubBioDesc.text = it.bio.toString() else tvGithubBioDesc.text = " - "
                tvGithubBlogDesc.text = it.blog
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .centerCrop()
                    .into(ivGithubProfile)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagersAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager2
        viewPager.adapter = sectionsPagersAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
        supportActionBar?.elevation = 0f
        sectionsPagersAdapter.username = username.toString()
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}