package com.drakjoakas.marketplatz.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drakjoakas.marketplatz.R
import com.drakjoakas.marketplatz.databinding.ActivityMainBinding
import com.drakjoakas.marketplatz.model.Producto
import com.drakjoakas.marketplatz.model.ProductoApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://www.serverbpw.com/"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productoApi : ProductoApi = retrofit.create(ProductoApi::class.java)
        val call: Call<List<Producto>> = productoApi.getGames("cm/2022-2/products.php")
    }
}