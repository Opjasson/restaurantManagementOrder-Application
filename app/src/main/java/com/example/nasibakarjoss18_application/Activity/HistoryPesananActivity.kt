package com.example.nasibakarjoss18_application.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasibakarjoss18_application.Adapter.CardHistoryAdapter
import com.example.nasibakarjoss18_application.DataStore.UserPreference
import com.example.nasibakarjoss18_application.R
import com.example.nasibakarjoss18_application.ViewModel.TransaksiViewModel
import com.example.nasibakarjoss18_application.ViewModel.UserViewModel
import com.example.nasibakarjoss18_application.databinding.ActivityHistoryPesananBinding
import kotlinx.coroutines.launch

class HistoryPesananActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryPesananBinding
    private val viewModel = TransaksiViewModel()
    private val userViewModel = UserViewModel()
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initSideBar()
        initGetTransaksi()


    }

    private fun initGetTransaksi () {
        userViewModel.getUserByUid()

        userViewModel.userLogin.observe(this) { user ->
            viewModel.loadTransaksiWithCart(user!!.documentId.toString())
        }


        viewModel.transaksiUI.observe(this){
                data ->
            Log.d("HISOTR", data.toString())
            binding.historyView.layoutManager= LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
            binding.historyView.adapter= CardHistoryAdapter(data.toMutableList())
            binding.loadHistory.visibility = View.GONE
        }


    }
    private fun initSideBar () {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout

        val navigationView = binding.navigationView

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.menu_manageProduct -> {
                    startActivity(Intent(this, ManageProductActivity::class.java))
                }
                R.id.menu_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                }
                R.id.menu_history -> {
                    startActivity(Intent(this, HistoryPesananActivity::class.java))
                }
//                R.id.menu_laporan -> {
//                    startActivity(Intent(this, LaporanTransactionActivity::class.java))
//                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

}