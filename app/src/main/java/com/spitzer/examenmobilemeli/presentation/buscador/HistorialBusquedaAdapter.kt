package com.spitzer.examenmobilemeli.presentation.buscador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.interfaces.IClickListener
import com.spitzer.examenmobilemeli.models.HistorialBusqueda
import kotlinx.android.synthetic.main.item_historial_busqueda.view.*

class HistorialBusquedaAdapter(historial: HistorialBusqueda, var listener: IClickListener) : RecyclerView.Adapter<HistorialBusquedaAdapter.ViewHolder>(){

    var items: ArrayList<String> = historial?.busqueda_string?: arrayListOf()
    var copiaItems: ArrayList<String> = ArrayList<String>(items)
    var viewHolder: ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_historial_busqueda, parent, false)
        viewHolder =
            ViewHolder(
                vista,
                listener
            )
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.tvBusqueda.text = item
        holder.bind(item)
    }

    fun filtrar(busqueda: String) {
        items.clear()

        if(busqueda.isEmpty()) {
            items.addAll(copiaItems)
        } else {
            val itemsOriginales = copiaItems
            items.addAll(itemsOriginales.filter{it.contains(busqueda)})
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, listener: IClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var vista = itemView
        var listener: IClickListener? = null

        init {
            this.listener = listener
            vista.setOnClickListener(this)
        }

        fun bind(item: String) {
            with(itemView) {
                tvBusqueda.text = item
            }
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}