package com.vanapic.food.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vanapic.food.R
import com.vanapic.food.core.ui.BeverageAdapter
import com.vanapic.food.core.ui.BeverageViewModelFactory
import com.vanapic.food.databinding.FragmentFavoriteBeverageBinding
import com.vanapic.food.ui.detail.DetailBeverageActivity
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


class FavoriteBeverageFragment : Fragment() {

    private lateinit var favoriteBeverageViewModel: FavoriteBeverageViewModel
    private var _binding: FragmentFavoriteBeverageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBeverageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = BeverageViewModelFactory.getInstance(requireActivity())
            favoriteBeverageViewModel = ViewModelProvider(this, factory)[FavoriteBeverageViewModel::class.java]
            refresh()
            getFavoriteBeverage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refresh(){
        val swipeRefresh = binding.swipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            getFavoriteBeverage()
        }
    }

    private fun getFavoriteBeverage(){
        val beverageAdapter = BeverageAdapter()
        beverageAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailBeverageActivity::class.java)
            intent.putExtra(DetailBeverageActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        loadingFood(true)
        favoriteBeverageViewModel.favoriteBeverage.observe(viewLifecycleOwner, { dataBeverage ->
            beverageAdapter.setData(dataBeverage)
            loadingFood(false)
            if (dataBeverage.isNotEmpty()){
                binding.noData.root.visibility =  View.GONE
            } else {
                binding.noData.root.visibility =  View.VISIBLE
                binding.noData.tvNoData.text = "No data available"
            }
        })

        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = beverageAdapter
        }
    }

    private fun loadingFood(state: Boolean){
        if(state){
            binding.rvFavorite.loadSkeleton(R.layout.food_item) { itemCount(10) }
        }else {
            binding.rvFavorite.hideSkeleton()
        }
    }
}