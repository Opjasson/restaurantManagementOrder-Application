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

//    fun uploadImageToCloudinary(uri: Uri) {
//        val file = uriToFile(this, uri)
//
//        val requestBody = MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart(
//                "file",
//                file.name,
//                file.asRequestBody("image/*".toMediaType())
//            )
//            .addFormDataPart("upload_preset", "UPLOAD_PRESET_KAMU")
//            .build()
//
//        val request = Request.Builder()
//            .url("https://api.cloudinary.com/v1_1/CLOUD_NAME_KAMU/image/upload")
//            .post(requestBody)
//            .build()
//
//        OkHttpClient().newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                runOnUiThread {
//                    Toast.makeText(this@MainActivity, "Upload gagal", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseBody = response.body?.string()
//                val json = JSONObject(responseBody ?: "")
//                val imageUrl = json.getString("secure_url")
//
//                runOnUiThread {
//                    showImage(imageUrl)
//                }
//            }
//        })
//    }
    fun initFormItem () {
        val pickImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
//                    uploadImageToCloudinary(it)
                    Log.d("URL", it.toString())
                }
            }

        fun uriToFile(context: Context, uri: Uri): File {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, "upload.jpg")

            inputStream.use { input ->
                file.outputStream().use { output ->
                    input?.copyTo(output)
                }
            }
            return file
        }

//        show data config
        viewModel.itemResult.observe(this){
                data ->
            binding.apply {
                nameItemFormTxt.setText(data[0].nama.toString())
                jumlahBarangForm.setText(data[0].jumlahBarang.toString())
                descEdt.setText(data[0].deskripsi.toString())
                descEdt.setText(data[0].deskripsi.toString())



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