package com.spitzer.examenmobilemeli.presentation.bandeja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.data.BusquedaArticulos
import com.spitzer.examenmobilemeli.data.Result
import com.spitzer.examenmobilemeli.interfaces.IClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_articulo_bandeja.view.*


class BandejaProductosAdapter(resultadoBusqueda: BusquedaArticulos, var listener: IClickListener)
    : RecyclerView.Adapter<BandejaProductosAdapter.ViewHolder>() {

    private var listaArticulos = resultadoBusqueda.results

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_articulo_bandeja, parent, false)
        return ViewHolder(
            vista,
            listener)
    }

    override fun getItemCount(): Int {
        return listaArticulos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articulo = listaArticulos[position]
        val condicion = when(articulo.condition) {
            "new" -> "Nuevo"
            "used" -> "Usado"
            "refubrished" -> "Reacondicionado"
            else -> ""
        }

        Picasso.get()
            .load(articulo.thumbnail)
            .placeholder(R.drawable.ic_image_24)
            .error(R.drawable.ic_broken_image_24)
            .into(holder.itemView.ivArticulo)

        holder.itemView.tvDescripcionArticulo.text = articulo.title
        holder.itemView.tvPrecio.text = articulo.price.toString()
        holder.itemView.tvCondicion.text = condicion
        holder.itemView.tvUbicacion.text = "${articulo.sellerAddress.state.name} - ${articulo.sellerAddress.city.name}"
        holder.itemView.tvEnvio.visibility = if (articulo.shipping.freeShipping) View.VISIBLE else View.GONE

        holder.itemView.clArticulo.setOnClickListener {
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(pLista: ArrayList<Result>) {
        listaArticulos.clear()
        listaArticulos.addAll(pLista)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, listener: IClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var vista = itemView
        var listener: IClickListener? = null

        init {
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}