package com.spitzer.examenmobilemeli.presentation.product

import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.data.Product
import com.spitzer.examenmobilemeli.utils.emptyString
import com.spitzer.examenmobilemeli.utils.toCash

class ProductViewModel : ViewModel() {
    lateinit var producto: Product
    var condicion: String = ""
    var vendidos: String = ""
    var formaPago: String = ""
    var productInfo: String = ""

    fun setearProducto(prod: Product) {
        this.producto = prod
        condicion = when (this.producto.condition) {
            "new" -> "Nuevo"
            "used" -> "Usado"
            "refubrished" -> "Reacondicionado"
            else -> ""
        }

        vendidos = when (this.producto.soldQuantity) {
            0 -> "ninguno vendido"
            1 -> "1 vendido"
            else -> "${this.producto.soldQuantity} vendidos"
        }

        formaPago =
            if (this.producto.installments != null &&
                this.producto.installments.amount != 0.0 &&
                this.producto.installments.quantity != 0
            ) {
                "Pagá hasta en " + this.producto.installments.quantity +
                        " cuotas de $" + this.producto.installments.amount.toCash()
            } else {
                String.Companion.emptyString
//                    .apply {
//                    if(producto.installments != null &&
//                        producto.installments.amount != 0.0 &&
//                        producto.installments.quantity != 0
//                    ) {
//                        this = "Pagá hasta en " + producto.installments.quantity +
//                                " cuotas de $" + producto.installments.amount.toCash()
//                }
            }

        productInfo =
            this.producto.attributes.map { "${it.name}: ${it.valueName}" }
                .joinToString("\n")
    }
}
