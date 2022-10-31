package com.vanapic.food.ui.beverage

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
import com.vanapic.food.core.ui.BeverageAdapter
import com.vanapic.food.core.ui.BeverageViewModelFactory
import com.vanapic.food.databinding.FragmentBeverageBinding
import com.vanapic.food.ui.detail.DetailBeverageActivity
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class BeverageFragment : Fragment() {

    private lateinit var beverageViewModel: BeverageViewModel
    private var _binding: FragmentBeverageBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeverageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null) {
            refresh()
            getBeverage()
            isSearch(true)
            getSearchBeverage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun getBeverage(){
        val beverageAdapter = BeverageAdapter()
        beverageAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailBeverageActivity::class.java)
            intent.putExtra(DetailBeverageActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        val factory = BeverageViewModelFactory.getInstance(requireActivity())
        beverageViewModel = ViewModelProvider(this, factory)[BeverageViewModel::class.java]
        beverageViewModel.beverage.observe(viewLifecycleOwner, { beverage ->
            if (beverage != null) {
                when (beverage) {
                    is Resource.Loading ->
                    {
                        loadingBeverage(true)
                    }
                    is Resource.Success -> {
                        loadingBeverage(false)
                        beverageAdapter.setData(beverage.data)
                    }
                    is Resource.Error -> {
                        loadingBeverage(false)

                    }
                }
            }
        })

        with(binding.rvBeverage) {
            layoutManager =   GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = beverageAdapter
        }
    }

    private fun loadingBeverage(state: Boolean){
        if(state){
            binding.rvBeverage.loadSkeleton(R.layout.food_item) { itemCount(10) }
        }else {
            binding.rvBeverage.hideSkeleton()
        }
    }


    private fun refresh(){
        val swipeRefresh = binding.swipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            getBeverage()
        }
    }

    private fun getSearchBeverage() {
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.search
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    getBeverageFromDB(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    isSearch(true)
                } else {
                    getBeverageFromDB(newText)
                }
                return false
            }
        })



    }

    @SuppressLint("SetTextI18n")
    private fun getBeverageFromDB(searchText: String) {
        var searchText = searchText
        searchText = "%$searchText%"
        val searhbeverageAdapter = BeverageAdapter()
        searhbeverageAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailBeverageActivity::class.java)
            intent.putExtra(DetailBeverageActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        beverageViewModel.getSearchBeverage(searchText).observe(viewLifecycleOwner, { list ->
            list?.let {
                searhbeverageAdapter.setData(list)
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
            adapter = searhbeverageAdapter
        }
    }

    private fun isSearch(state:Boolean) {
        if(state){
            binding.noData.root.visibility = View.GONE
            binding.rvBeverage.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.GONE
        }else {
            binding.noData.root.visibility = View.GONE
            binding.rvBeverage.visibility = View.GONE
            binding.rvSearch.visibility = View.VISIBLE
        }
    }
}