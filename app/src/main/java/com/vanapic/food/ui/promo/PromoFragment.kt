package com.vanapic.food.ui.promo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vanapic.food.R
import com.vanapic.food.core.data.Resource
import com.vanapic.food.core.ui.PromoAdapter
import com.vanapic.food.core.ui.PromoViewModelFactory
import com.vanapic.food.databinding.FragmentPromoBinding
import com.vanapic.food.ui.detail.DetailBeverageActivity
import com.vanapic.food.ui.detail.DetailFoodActivity
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class PromoFragment : Fragment() {

    private lateinit var promoViewModel: PromoViewModel
    private var _binding: FragmentPromoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity != null) {
            refresh()
            getPromo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPromo(){
        val promoAdapter = PromoAdapter()
        promoAdapter.onItemClick = { selectedData ->
        }

        val factory = PromoViewModelFactory.getInstance(requireActivity())
        promoViewModel = ViewModelProvider(this, factory)[PromoViewModel::class.java]
        promoViewModel.promo.observe(viewLifecycleOwner, { promo ->
            if (promo != null) {
                when (promo) {
                    is Resource.Loading ->
                    {
                        loadingPromo(true)
                    }
                    is Resource.Success -> {
                        loadingPromo(false)
                        promoAdapter.setData(promo.data)
                    }
                    is Resource.Error -> {
                        loadingPromo(false)

                    }
                }
            }
        })

        with(binding.rvPromo) {
            layoutManager =   GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = promoAdapter
        }
    }

    private fun loadingPromo(state: Boolean){
        if(state){
            binding.rvPromo.loadSkeleton(R.layout.item_promo) { itemCount(10) }
        }else {
            binding.rvPromo.hideSkeleton()
        }
    }


    private fun refresh(){
        val swipeRefresh = binding.swipeRefreshLayout
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            getPromo()
        }
    }

}