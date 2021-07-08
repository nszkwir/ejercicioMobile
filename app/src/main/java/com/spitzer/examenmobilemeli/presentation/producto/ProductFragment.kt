package com.spitzer.examenmobilemeli.presentation.producto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.databinding.FragmentProductBinding
import com.spitzer.examenmobilemeli.utils.toCash
import com.squareup.picasso.Picasso

class ProductFragment : Fragment() {
    private val args: ProductFragmentArgs by navArgs()
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        ).get(ProductViewModel::class.java)
        mViewModel.setearProducto(args.product)
        defineBindings()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun defineBindings() {

        binding.tvCondicionArticulo.text = mViewModel.condicion
        binding.tvCantidadVendidos.text = mViewModel.vendidos
        binding.tvTituloArticulo.text = mViewModel.producto.title

        Picasso.get()
            .load(mViewModel.producto.thumbnail)
            .placeholder(R.drawable.ic_image_24)
            .error(R.drawable.ic_broken_image_24)
            .into(binding.ivImagenArticulo)

        binding.tvPrecioArticulo.text = mViewModel.producto.price.toCash()

        binding.clEnvio.visibility =
            if (mViewModel.producto.shipping.freeShipping) View.VISIBLE else View.GONE

        if (mViewModel.formaPago.isNotBlank()) {
            binding.clPago.visibility = View.VISIBLE
            binding.tvPago.text = mViewModel.formaPago
        } else {
            binding.clPago.visibility = View.GONE
        }

        if (mViewModel.producto.attributes.isNullOrEmpty()) {
            binding.vDivisorHorizontalInfo.visibility = View.GONE
            binding.clInformacionProducto.visibility = View.GONE
        } else {
            binding.vDivisorHorizontalInfo.visibility = View.VISIBLE
            binding.clInformacionProducto.visibility = View.VISIBLE

            binding.tvInfoKeyValue.text = mViewModel.productInfo
        }
    }
}
