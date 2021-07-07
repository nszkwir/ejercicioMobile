package com.spitzer.examenmobilemeli.presentation.producto

import androidx.lifecycle.ViewModel
import com.spitzer.examenmobilemeli.data.Result
import com.spitzer.examenmobilemeli.utils.emptyString
import com.spitzer.examenmobilemeli.utils.toCash

class ProductoViewModel : ViewModel() {
    lateinit var producto: Result
    var condicion: String = ""
    var vendidos: String = ""
    var formaPago: String = ""
    var productInfo: String = ""

    fun setearProducto(prod: Result) {
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
