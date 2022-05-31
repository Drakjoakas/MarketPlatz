package com.drakjoakas.marketplatz.view.ui

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.drakjoakas.marketplatz.R
import com.drakjoakas.marketplatz.databinding.ActivityDetallesBinding
import com.drakjoakas.marketplatz.model.ProductoApi
import com.drakjoakas.marketplatz.model.ProductoDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Detalles : AppCompatActivity() {

    private lateinit var binding: ActivityDetallesBinding

    private val BASE_URL = "https://www.serverbpw.com/"
    private val LOGTAG   = "LOGS"

    private var precio:    String? = null
    private var envio:     String? = null
    private var proveedor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getString("id","0")
        precio = bundle?.getString("precio","59.99")
        envio  = bundle?.getString("envio","50.00")
        proveedor = bundle?.getString("proveedor","Amazon")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productoApi: ProductoApi = retrofit.create(ProductoApi::class.java)

        val call: Call<ProductoDetail> = productoApi.getProductDetail(id)

        call.enqueue(object: Callback<ProductoDetail>{
            override fun onFailure(call: Call<ProductoDetail>, t: Throwable) {
                Log.d(LOGTAG,"Error")
                binding.pbConexion.visibility = View.INVISIBLE
                Toast.makeText(this@Detalles, "No hay conexión", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ProductoDetail>,
                response: Response<ProductoDetail>
            ) {
                with(binding) {
                    tvNombre.text = response.body()?.name
                    Glide.with(this@Detalles)
                        .load(response.body()?.image)
                        .into(ivDetalle)
                    tvDescripcion.text = response.body()?.description
                    pbConexion.visibility = View.INVISIBLE
                    tvPrecio.text = getString(R.string.precio,precio)
                    tvEnvio.text = getString(R.string.envio,envio)
                    tvProvider.text = proveedor
                }

            }

        })
    }

    fun onClik(view: View) {
        var builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.mensaje_dialog_carrito))
            .setTitle(R.string.titulo_dialog_carrito)
            .setPositiveButton(R.string.cerrar_dialog) {
                dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
}