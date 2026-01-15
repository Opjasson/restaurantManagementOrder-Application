package com.example.nasibakarjoss18_application.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nasibakarjoss18_application.Activity.DetailActivity
import com.example.nasibakarjoss18_application.Activity.ItemByKategoriActivity
import com.example.nasibakarjoss18_application.Domain.KategoriModel
import com.example.nasibakarjoss18_application.databinding.ViewholderKategoriBinding


class KategoriAdapter(val items : MutableList<KategoriModel>)
    : RecyclerView.Adapter<KategoriAdapter.Viewholder>() {

        private lateinit var context: Context

    inner class Viewholder(val binding: ViewholderKategoriBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderKategoriBinding
            .inflate(LayoutInflater
            .from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: KategoriAdapter.Viewholder, position: Int) {
        val item = items[position]
        Log.d("itemKatgeori", item.toString())
        holder.binding.titleCat.text = item.title
        holder.binding.titleCat.setOnClickListener {
            val intent = Intent(context, ItemByKategoriActivity::class.java).apply {
                putExtra("id", item.kategoriId)
                putExtra("nama", item.title)
            }
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int = items.size
}