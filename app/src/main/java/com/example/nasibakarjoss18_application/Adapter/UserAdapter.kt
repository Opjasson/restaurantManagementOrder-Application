package com.example.nasibakarjoss18_application.Adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nasibakarjoss18_application.Domain.UsersModel
import com.example.nasibakarjoss18_application.databinding.ViewholderUserListBinding


class UserAdapter(val items: MutableList<UsersModel>):
    RecyclerView.Adapter<UserAdapter.Viewholder>(){

    private lateinit var context: Context

    inner class Viewholder( val binding: ViewholderUserListBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.Viewholder {
        context= parent.context
        val binding = ViewholderUserListBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.Viewholder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            ULUsernameTxt.text = item.username.replaceFirstChar { it.uppercase() }
            ULEmailTxt.text = item.email
        }

//        holder.binding.popularCard.setOnClickListener {
//            Handler(Looper.getMainLooper()).postDelayed({
//                val intent = Intent(context, DetailActivity::class.java).apply {
//                    putExtra("id", item.documentId)
//                    putExtra("nama", item.nama)
//                }
//                ContextCompat.startActivity(context, intent, null)
//            }, 500)
//
//        }

    }
    override fun getItemCount(): Int = items.size

    fun updateData(newItems: MutableList<UsersModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}