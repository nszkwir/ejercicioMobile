package com.spitzer.examenmobilemeli.presentation.productSearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spitzer.examenmobilemeli.databinding.ItemSearchHistoryBinding
import com.spitzer.examenmobilemeli.models.SearchHistory
import com.spitzer.examenmobilemeli.utils.listenToClick

class SearchHistoryAdapter(searchHistory: SearchHistory) :
    RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder>() {

    var items: ArrayList<String> = searchHistory.busqueda_string
    private var itemsCopy: ArrayList<String> = ArrayList(items)
    lateinit var onItemClick: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val item =
            ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item).listenToClick { position, _ ->
            onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
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

    fun onItemClickFunction(itemClickFunction: (String) -> Unit) {
        onItemClick = itemClickFunction
    }

    inner class ViewHolder(
        private val itemBinding: ItemSearchHistoryBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: String) {
            itemBinding.tvBusqueda.text = item
        }
    }
}
