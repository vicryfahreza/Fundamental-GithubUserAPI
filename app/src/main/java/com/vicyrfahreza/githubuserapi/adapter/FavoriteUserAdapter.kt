package com.vicyrfahreza.githubuserapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vicyrfahreza.githubuserapi.DetailUserActivity
import com.vicyrfahreza.githubuserapi.databinding.ItemGithubUserBinding
import com.vicyrfahreza.githubuserapi.response.FavoriteUserResponse

class FavoriteUserAdapter(private var listGithubUser: ArrayList<FavoriteUserResponse>): RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FavoriteUserResponse){
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(imgUser)
                tvName.text = user.login

                itemView.setOnClickListener {
                    val detailUserIntent = Intent(itemView.context, DetailUserActivity::class.java)
                    detailUserIntent.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    detailUserIntent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, user.avatarUrl)
                    itemView.context.startActivity(detailUserIntent)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ItemGithubUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listGithubUser[position])
    }

    override fun getItemCount(): Int = listGithubUser.size

}