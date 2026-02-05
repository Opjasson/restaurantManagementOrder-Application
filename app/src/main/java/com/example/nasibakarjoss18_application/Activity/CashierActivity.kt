package com.example.nasibakarjoss18_application.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.nasibakarjoss18_application.R
import com.example.nasibakarjoss18_application.databinding.ActivityCashierBinding

class CashierActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding : ActivityCashierBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCashierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initSideBar()
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
//                R.id.menu_manageProduct -> {
//                    startActivity(Intent(this, ManageProductActivity::class.java))
//                }
//                R.id.menu_cart -> {
//                    startActivity(Intent(this, CartActivity::class.java))
//                }
//                R.id.menu_history -> {
//                    startActivity(Intent(this, HistoryTransaksiActivity::class.java))
//                }
//                R.id.menu_laporan -> {
//                    startActivity(Intent(this, LaporanTransactionActivity::class.java))
//                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}