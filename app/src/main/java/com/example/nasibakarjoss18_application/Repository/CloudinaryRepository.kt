package com.example.nasibakarjoss18_application.Repository

import android.content.Context
import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.Request
import java.io.File

class CloudinaryRepository {
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

    fun uploadImageToCloudinary(uri: Uri) {
        val file = uriToFile(this, uri)

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                file.name,
                file.asRequestBody("image/*".toMediaType())
            )
            .addFormDataPart("upload_preset", "UPLOAD_PRESET_KAMU")
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/CLOUD_NAME_KAMU/image/upload")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Upload gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val json = JSONObject(responseBody ?: "")
                val imageUrl = json.getString("secure_url")

                runOnUiThread {
                    showImage(imageUrl)
                }
            }
        })
    }
}