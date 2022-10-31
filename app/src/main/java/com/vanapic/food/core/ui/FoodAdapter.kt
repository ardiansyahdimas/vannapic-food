package com.vanapic.food.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanapic.food.R
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.databinding.FoodItemBinding
import java.util.ArrayList

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ListViewHolder>() {

    private var listData = ArrayList<Food>()
    var onItemClick: ((Food) -> Unit)? = null

    fun setData(newListData: List<Food>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FoodItemBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bind(data: Food) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.photo)
                    .into(photo)
                name.text = data.name
                price.text = "Rp. ${data.price}"
                stock.text = "${data.stock} pcs"
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

}