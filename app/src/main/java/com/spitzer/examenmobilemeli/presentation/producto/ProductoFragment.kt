package com.spitzer.examenmobilemeli.presentation.producto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.databinding.FragmentProductoBinding


class ProductoFragment : Fragment() {
    private lateinit var binding: FragmentProductoBinding
    private lateinit var mViewModel: ProductoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bandeja_productos, container, false)
        mViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(ProductoViewModel::class.java)
        definirBindings()
        return binding.root
    }

    fun definirBindings() {

    }

}
