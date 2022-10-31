package com.vanapic.food.ui.food

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vanapic.food.R
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.ui.FoodAdapter
import com.vanapic.food.core.ui.FoodViewModelFactory
import com.vanapic.food.databinding.FragmentFoodBinding
import com.vanapic.food.ui.detail.DetailBeverageActivity
import com.vanapic.food.ui.detail.DetailFoodActivity
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class FoodFragment : Fragment() {

    private lateinit var foodViewModel: FoodViewModel
    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null) {
            refresh()
            getFood()
            isSearch(true)
            getSearchFood()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFood(){
        val foodAdapter = FoodAdapter()
        foodAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailFoodActivity::class.java)
            intent.putExtra(DetailFoodActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        val factory = FoodViewModelFactory.getInstance(requireActivity())
        foodViewModel = ViewModelProvider(this, factory)[FoodViewModel::class.java]
        foodViewModel.food.observe(viewLifecycleOwner, { food ->
            if (food != null) {
                when (food) {
                    is Resource.Loading ->
                    {
                        loadingFood(true)
                    }
                    is Resource.Success -> {
                        loadingFood(false)
                        foodAdapter.setData(food.data)
                    }
                    is Resource.Error -> {
                        loadingFood(false)

                    }
                }
            }
        })

        with(binding.rvFood) {
            layoutManager =   GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = foodAdapter
        }
    }

    private fun loadingFood(state: Boolean){
        if(state){
            binding.rvFood.loadSkeleton(R.layout.food_item) { itemCount(10) }
        }else {
            binding.rvFood.hideSkeleton()
        }
    }


    private fun refresh(){
        val swipeRefresh = binding.swipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            getFood()
        }
    }

    private fun getSearchFood() {
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.search
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    getFoodFromDB(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    isSearch(true)
                } else {
                    getFoodFromDB(newText)
                }
                return false
            }
        })



    }

    @SuppressLint("SetTextI18n")
    private fun getFoodFromDB(searchText: String) {
        var searchText = searchText
        searchText = "%$searchText%"
        val searhfoodAdapter = FoodAdapter()
        searhfoodAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailFoodActivity::class.java)
            intent.putExtra(DetailFoodActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        foodViewModel.getSearchFood(searchText).observe(viewLifecycleOwner, { list ->
            list?.let {
                searhfoodAdapter.setData(list)
                isSearch(false)
                if(list.isEmpty()){
                    binding.noData.root.visibility = View.VISIBLE
                    val text = searchText.replace("%","")
                    binding.noData.tvNoData.text = "Can't find $text"
                }
            }
        })
        with(binding.rvSearch) {
            layoutManager =   GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = searhfoodAdapter
        }
    }

    private fun isSearch(state:Boolean) {
        if(state){
            binding.noData.root.visibility = View.GONE
            binding.rvFood.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.GONE
        }else {
            binding.noData.root.visibility = View.GONE
            binding.rvFood.visibility = View.GONE
            binding.rvSearch.visibility = View.VISIBLE
        }
    }
}