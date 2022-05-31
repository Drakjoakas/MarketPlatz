package com.drakjoakas.marketplatz.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drakjoakas.marketplatz.databinding.ListElementBinding
import com.drakjoakas.marketplatz.model.Producto

class Adaptador(context: Context, productos: List<Producto>, onItemListener: OnItemListener): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    private val productos = productos
    private val mOnItemListener = onItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adaptador.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = ListElementBinding.inflate(layoutInflater)

        return ViewHolder(binding,mOnItemListener)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(holder: Adaptador.ViewHolder, position: Int) {
        holder.bindData(productos[position])
    }

    interface OnItemListener {
        fun onItemClick(producto: Producto)
    }

    class ViewHolder(binding: ListElementBinding, onItemListener: OnItemListener): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private val tvTitle = binding.tvTitulo
        private val ivProducto  = binding.ivProducto
        private val tvProveedor = binding.tvProveedor
        private val tvPrecio    = binding.tvPrecio
        private val tvEnvio     = binding.tvEnvio

        private val context = binding.root.context
        private val onItemListener = onItemListener
        private lateinit var producto: Producto

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(item: Producto) {
            tvTitle.text      = item.name
            tvProveedor.text  = item.provider
            tvEnvio.text      = "Envío: +$${item.delivery}"
            tvPrecio.text     = "$${item.price}"

            Glide.with(context)
                .load(item.thumbnail)
                .into(ivProducto)
            producto = item
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(producto)

        }
    }

}


