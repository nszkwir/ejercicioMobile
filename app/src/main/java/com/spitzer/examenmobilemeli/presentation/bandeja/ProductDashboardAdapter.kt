package com.spitzer.examenmobilemeli.presentation.bandeja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.data.Product
import com.spitzer.examenmobilemeli.data.ProductSearch
import com.spitzer.examenmobilemeli.databinding.ItemDashboardProductBinding
import com.spitzer.examenmobilemeli.utils.listenToClick
import com.spitzer.examenmobilemeli.utils.toCash
import com.squareup.picasso.Picasso

class ProductDashboardAdapter(productSearch: ProductSearch) :
    RecyclerView.Adapter<ProductDashboardAdapter.ViewHolder>() {

    private var productList = productSearch.results
    lateinit var onItemClick: (Product) -> Unit

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val item =
            ItemDashboardProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item).listenToClick { position, _ ->
            onItemClick(productList[position])
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(pLista: ArrayList<Product>) {
        productList.clear()
        productList.addAll(pLista)
        notifyDataSetChanged()
    }

    fun onItemClickFunction(itemClickFunction: (Product) -> Unit) {
        onItemClick = itemClickFunction
    }

    inner class ViewHolder(
        private val itemBinding: ItemDashboardProductBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: Product) {
            val condition = when (product.condition) {
                "new" -> "Nuevo"
                "used" -> "Usado"
                "refubrished" -> "Reacondicionado"
                else -> ""
            }

            Picasso.get()
                .load(product.thumbnail)
                .placeholder(R.drawable.ic_image_24)
                .error(R.drawable.ic_broken_image_24)
                .into(itemBinding.ivArticulo)

            itemBinding.tvDescripcionArticulo.text = product.title
            itemBinding.tvPrecio.text = product.price.toCash()
            itemBinding.tvCondicion.text = condition
            itemBinding.tvUbicacion.text =
                "${product.sellerAddress.state.name} - ${product.sellerAddress.city.name}"
            itemBinding.tvEnvio.visibility =
                if (product.shipping.freeShipping) View.VISIBLE else View.GONE
        }
    }
}
