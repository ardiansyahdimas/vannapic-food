package com.vanapic.food.core.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanapic.food.R
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.domain.model.Promo
import com.vanapic.food.databinding.ItemPromoBinding
import java.util.ArrayList

class PromoAdapter : RecyclerView.Adapter<PromoAdapter.ListViewHolder>() {

    private var listData = ArrayList<Promo>()
    var onItemClick: ((Promo) -> Unit)? = null

    fun setData(newListData: List<Promo>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_promo, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPromoBinding.bind(itemView)
        @SuppressLint("SetTextI18n")
        fun bind(data: Promo) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.photo)
                    .into(photo)
                name.text = data.name
                currentPrice.text = "Rp. ${data.price}"
                currentPrice.setPaintFlags(currentPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                promoPrice.text = "Rp. ${data.price - 2000}"
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