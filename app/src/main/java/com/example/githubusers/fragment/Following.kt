package com.example.githubusers.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.ui.detailActivity.DetailViewModel
import com.example.githubusers.ViewModelFactory
import com.example.githubusers.adapter.FollowerAdapter
import com.example.githubusers.databinding.FragmentFollowingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Following.newInstance] factory method to
 * create an instance of this fragment.
 */
class Following : Fragment() {
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val adapter = FollowerAdapter()
    private lateinit var bind: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFollowingBinding.inflate(inflater, container, false)

        with(bind){
            ListFollowing.layoutManager = LinearLayoutManager(requireActivity())
            ListFollowing.adapter = adapter

            val dataNama = arguments?.getString(NAMA)

            detailViewModel.getFollowing(dataNama!!).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            detailViewModel.getLoading().observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
        return bind.root
    }
    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {

        fun newInstance(dataNama: String): Following {
            val fragment = Following()
            val args = Bundle()
            args.putString(NAMA, dataNama)
            fragment.arguments = args
            return fragment
        }

        const val NAMA = "nama"
    }
}