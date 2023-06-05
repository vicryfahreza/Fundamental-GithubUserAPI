package com.vicyrfahreza.githubuserapi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicyrfahreza.githubuserapi.adapter.GithubUserAdapter
import com.vicyrfahreza.githubuserapi.databinding.FragmentFollowerBinding
import com.vicyrfahreza.githubuserapi.model.FollowerViewModel


class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: GithubUserAdapter
    private val viewModel: FollowerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if(position == 0){
            viewModel.setListFollowers(username)
            viewModel.getListFollowers().observe(viewLifecycleOwner) {
                adapter = GithubUserAdapter(it)
                binding.apply {
                    rvUser.layoutManager = LinearLayoutManager(requireActivity())
                    rvUser.setHasFixedSize(true)
                    rvUser.adapter = adapter
                }
            }
        } else {
            viewModel.setListFollowing(username)
            viewModel.getListFollowing().observe(viewLifecycleOwner) {
                adapter = GithubUserAdapter(it)
                binding.apply {
                    rvUser.layoutManager = LinearLayoutManager(requireActivity())
                    rvUser.setHasFixedSize(true)
                    rvUser.adapter = adapter
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object{
        var username = ""
        var position = 0
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}