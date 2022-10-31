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
import com.vanapic.food.core.ui.FoodAdapter
import com.vanapic.food.core.ui.FoodViewModelFactory
import com.vanapic.food.databinding.FragmentFavoriteFoodBinding
import com.vanapic.food.ui.detail.DetailFoodActivity
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


class FavoriteFoodFragment : Fragment() {

    private lateinit var favoriteFoodViewModel: FavoriteFoodViewModel
    private var _binding: FragmentFavoriteFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = FoodViewModelFactory.getInstance(requireActivity())
            favoriteFoodViewModel = ViewModelProvider(this, factory)[FavoriteFoodViewModel::class.java]
            refresh()
            getFavoriteFood()
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
              getFavoriteFood()
        }
    }

    private fun getFavoriteFood(){
        val foodAdapter = FoodAdapter()
        foodAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailFoodActivity::class.java)
            intent.putExtra(DetailFoodActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        loadingFood(true)
        favoriteFoodViewModel.favoriteFood.observe(viewLifecycleOwner, { dataFood ->
            foodAdapter.setData(dataFood)
            loadingFood(false)
           if (dataFood.isNotEmpty()){
               binding.noData.root.visibility =  View.GONE
            } else {
               binding.noData.root.visibility =  View.VISIBLE
               binding.noData.tvNoData.text = "No data available"
            }
        })

        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = foodAdapter
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