package com.spitzer.examenmobilemeli.presentation.buscador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.interfaces.IClickListener
import com.spitzer.examenmobilemeli.models.SearchHistory

class SearchHistoryAdapter(searchHistory: SearchHistory, var listener: IClickListener) :
    RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {

    var items: ArrayList<String> = searchHistory.busqueda_string
    var itemsCopy: ArrayList<String> = ArrayList(items)
    var viewHolder: ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_history, parent, false)
        viewHolder =
            ViewHolder(
                view,
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

    fun filter(searchString: String) {
        items.clear()
        if (searchString.isEmpty()) {
            items.addAll(itemsCopy)
        } else {
            items.addAll(itemsCopy.filter { it.contains(searchString) })
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, listener: IClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var view = itemView
        var listener: IClickListener? = null

        init {
            this.listener = listener
            view.setOnClickListener(this)
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
