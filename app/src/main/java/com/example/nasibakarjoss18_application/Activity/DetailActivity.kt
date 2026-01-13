package com.example.nasibakarjoss18_application.Activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.nasibakarjoss18_application.R
import com.example.nasibakarjoss18_application.ViewModel.PopularViewModel
import com.example.nasibakarjoss18_application.databinding.ActivityDetailBinding
import okhttp3.MultipartBody
import java.io.File

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: PopularViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initFormItem()
    }

    fun initFormItem () {
        val pickImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    Log.d("URL", it.toString())
                    Glide.with(applicationContext).load(uri).into(binding.picItem)
                    binding.editBtn.setOnClickListener {
                        viewModel.upload(this, uri)
                    }
                }
            }

//        show data config
        viewModel.itemResult.observe(this){
                data ->
            binding.apply {
                nameItemFormTxt.setText(data[0].nama.toString())
                jumlahBarangForm.setText(data[0].jumlahBarang.toString())
                descEdt.setText(data[0].deskripsi.toString())
                descEdt.setText(data[0].deskripsi.toString())
                Glide.with(applicationContext).load(data[0].imgUrl).into(binding.picItem)

                plusBtn.setOnClickListener {
                    val current = jumlahBarangForm.text.toString().toIntOrNull() ?: 0
                    val newJumlah = current + 1
                    jumlahBarangForm.setText(newJumlah.toString())
                }
                minBtn.setOnClickListener {
                    val current = jumlahBarangForm.text.toString().toIntOrNull() ?: 0
                    val newJumlah = current - 1
                    jumlahBarangForm.setText(newJumlah.toString())
                }

                gambarBarangForm.setOnClickListener {
                    pickImage.launch("image/*")
                }


            }
        }
        viewModel.loadData(intent.getLongExtra("id", 1)!!)

//    Setting drop down
        val items = listOf("Populer", "Tidak")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            items
        )

        if (binding.dropdownMenu.text.isNullOrEmpty()) {
            binding.dropdownLayout.error = "Harus dipilih"
        } else {
            binding.dropdownLayout.error = null
        }

        binding.dropdownMenu.setAdapter(adapter)

        binding.dropdownMenu.setOnItemClickListener { _, _, position, _ ->
            val selected = items[position]
        }



        binding.backBtn.setOnClickListener {
            finish()
        }
    }

}