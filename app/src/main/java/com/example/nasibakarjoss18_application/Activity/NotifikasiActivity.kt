package com.example.nasibakarjoss18_application.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasibakarjoss18_application.Adapter.AlatMakanAdapter
import com.example.nasibakarjoss18_application.Adapter.ItemsAdapter
import com.example.nasibakarjoss18_application.Adapter.PopularAdapter
import com.example.nasibakarjoss18_application.R
import com.example.nasibakarjoss18_application.ViewModel.PopularViewModel
import com.example.nasibakarjoss18_application.databinding.ActivityNotifikasiBinding

class NotifikasiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNotifikasiBinding

    val viewModel = PopularViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)

        updateBottomNavIcon(R.id.notif)

        binding.bottomNav.selectedItemId = R.id.notif
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initAlatMakan()
        initAlatMasak()
        initAlatCuci()

        //        Navigate bottom setting
        binding.bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == binding.bottomNav.selectedItemId) {
                binding.bottomNav.menu.findItem(R.id.notif).icon =
                    ContextCompat.getDrawable(this, R.drawable.bellcolor)
                return@setOnItemSelectedListener true
            }


            when (item.itemId) {
                R.id.main -> startActivity(Intent(this, MainActivity::class.java))
                R.id.search -> startActivity(Intent(this, SearchActivity::class.java))
                R.id.notif -> startActivity(Intent(this, NotifikasiActivity::class.java))
            }
            true
        }
    }

    fun initAlatMakan() {
        var adapterAlatMakan: AlatMakanAdapter
        var viewModelPopular: PopularViewModel

        // 1. INIT VIEWMODEL
        viewModelPopular = ViewModelProvider(this)[PopularViewModel::class.java]

        // 2. INIT ADAPTER
        adapterAlatMakan = AlatMakanAdapter()

        // 3. SET RECYCLERVIEW
        binding.alatMakanView.apply {
            layoutManager = LinearLayoutManager(this@NotifikasiActivity,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = adapterAlatMakan
        }

        // 4. OBSERVE DATA
        viewModelPopular.alatMakanResult.observe(this) { list ->
            binding.loadAlatMakan.visibility = View.GONE
            adapterAlatMakan.setData(list)
        }

        // 5. PANGGIL DATA
        viewModelPopular.getAlatMakan()
    }

//    get alat masak
    fun initAlatMasak() {
        var adapterAlatMasak: AlatMakanAdapter
        var viewModelPopular: PopularViewModel

        // 1. INIT VIEWMODEL
        viewModelPopular = ViewModelProvider(this)[PopularViewModel::class.java]

        // 2. INIT ADAPTER
        adapterAlatMasak = AlatMakanAdapter()

        // 3. SET RECYCLERVIEW
        binding.alatMasakView.apply {
            layoutManager = LinearLayoutManager(this@NotifikasiActivity,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = adapterAlatMasak
        }

        // 4. OBSERVE DATA
        viewModelPopular.alatMasakResult.observe(this) { list ->
            binding.loadAlatMasak.visibility = View.GONE
            adapterAlatMasak.setData(list)
        }

        // 5. PANGGIL DATA
        viewModelPopular.getAlatMasak()
    }

    //    get alat masak
    fun initAlatCuci() {
        var adapterAlatCuci: AlatMakanAdapter
        var viewModelPopular: PopularViewModel

        // 1. INIT VIEWMODEL
        viewModelPopular = ViewModelProvider(this)[PopularViewModel::class.java]

        // 2. INIT ADAPTER
        adapterAlatCuci = AlatMakanAdapter()

        // 3. SET RECYCLERVIEW
        binding.alatCuciView.apply {
            layoutManager = LinearLayoutManager(this@NotifikasiActivity,
                LinearLayoutManager.VERTICAL, false
            )
            adapter = adapterAlatCuci
        }

        // 4. OBSERVE DATA
        viewModelPopular.alatCuciResult.observe(this) { list ->
            Log.d("LISTCUCI", list.toString())
            binding.loadAlatCuci.visibility = View.GONE
            adapterAlatCuci.setData(list)
        }

        // 5. PANGGIL DATA
        viewModelPopular.getAlatCuci()
    }

    //    bottom Nav Setting
    private fun updateBottomNavIcon(activeItemId: Int) {
        val menu = binding.bottomNav.menu

        // Home
        menu.findItem(R.id.notif).icon =
            ContextCompat.getDrawable(
                this,
                if (activeItemId == R.id.notif)
                    R.drawable.bellcolor
                else
                    R.drawable.bell
            )

    }
}