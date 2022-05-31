package com.drakjoakas.marketplatz.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakjoakas.marketplatz.R
import com.drakjoakas.marketplatz.databinding.ActivityMainBinding
import com.drakjoakas.marketplatz.model.Producto
import com.drakjoakas.marketplatz.model.ProductoApi
import com.drakjoakas.marketplatz.view.adapter.Adaptador
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Adaptador.OnItemListener {

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG   = "LOGS"

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
        val call: Call<List<Producto>> = productoApi.getProducts("cm/2022-2/products.php")

        call.enqueue(object: Callback<List<Producto>>{
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                Log.d(LOGTAG,"Respuesta del servidor: ${response.toString()} ")
                Log.d(LOGTAG,"Datos ${response.body().toString()}")

                //Barra de carga
                binding.pbConexion.visibility = View.INVISIBLE

                val adaptador = Adaptador(this@MainActivity,response.body()!!,this@MainActivity)
                val recyclerView = binding.rvMenu
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adaptador
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.d(LOGTAG, "Error")
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity,"No hay conexión", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onItemClick(producto: Producto) {
        val parametros = Bundle()

        parametros.putString("id",producto.id)

        val intent = Intent(this, Detalles::class.java)
        intent.putExtras(parametros)
        startActivity(intent)
    }
}