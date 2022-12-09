package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRandomImageBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRandomImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        randomImage()
        binding.btnRandomImage2.setOnClickListener {
            binding.ivLoadingRandomImage.visibility = View.INVISIBLE
            binding.ivLoadingRandomImage.visibility = View.VISIBLE
            randomImage()
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun randomImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(IAPIService::class.java).getRandomImage()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    binding.ivLoadingRandomImage.visibility = View.INVISIBLE
                    val image = puppies?.message as String
                    Picasso.get().load(image).into(binding.ivRandomImage)
                    binding.ivRandomImage.visibility = View.VISIBLE
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Log.d("ERROR", "ERROR OCURRED")
        Toast.makeText(applicationContext, "Error ocurred", Toast.LENGTH_SHORT).show()
    }
}