package com.spitzer.examenmobilemeli.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.spitzer.examenmobilemeli.MainActivity
import com.spitzer.examenmobilemeli.data.Product
import com.spitzer.examenmobilemeli.databinding.FragmentDashboardProductsBinding
import com.spitzer.examenmobilemeli.presentation.searchhistory.SearchHistoryViewModel
import com.spitzer.examenmobilemeli.presentation.searchhistory.SearchHistoryViewModelFactory
import com.spitzer.examenmobilemeli.utils.AppConstants
import com.spitzer.examenmobilemeli.utils.observeEvent
import com.spitzer.network.ViewState

class ProductDashboardFragment : Fragment() {

    private var _binding: FragmentDashboardProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productDashboardViewModel: ProductDashboardViewModel
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel
    private lateinit var productDashboardAdapter: ProductDashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productDashboardViewModel = ViewModelProvider(
            this,
            ProductDashboardViewModelFactory()
        ).get(ProductDashboardViewModel::class.java)

        searchHistoryViewModel = ViewModelProvider(
            requireActivity(),
            SearchHistoryViewModelFactory(requireContext())
        ).get(SearchHistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardProductsBinding.inflate(inflater, container, false)
        productDashboardAdapter = ProductDashboardAdapter(productDashboardViewModel.searchResults)
        productDashboardAdapter.onItemClickFunction { product -> navigateToProductFragment(product) }
        defineObservables()
        defineBindings()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToProductFragment(product: Product) {
        val action =
            ProductDashboardFragmentDirections
                .actionProductDashboardFragmentToProductFragment(product)
        findNavController().navigate(action)
    }

    private fun defineBindings() {

        searchHistoryViewModel.obtainSearchHistory()

        if (binding.rvProductos.adapter == null) {
            binding.rvProductos.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = productDashboardAdapter
            }
        }

        binding.searchCard.onClickFunction {
            val action =
                ProductDashboardFragmentDirections
                    .actionProductDashboardFragmentToSearchFragment(
                        searchHistoryViewModel.searchHistory
                    )
            findNavController().navigate(action)
        }

        binding.clBarraCantidadResultados.visibility =
            if (productDashboardViewModel.searchResults.results.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.tvCantidadBusqueda.text =
            "${productDashboardViewModel.searchResults.results.size} resultados"
    }

    private fun defineObservables() {

        searchHistoryViewModel.search.observeEvent(viewLifecycleOwner) { searchText ->

            if (searchText.isNotBlank()) {
                binding.searchCard.setText(searchText)
                searchHistoryViewModel.updateSearchHistory(searchText)
                binding.clBarraCantidadResultados.visibility = View.GONE
                productDashboardViewModel.searchProducts(searchText)
            } else {
                binding.searchCard.setText("Buscar en Mercado Libre ...")
            }
        }

        productDashboardViewModel.searchResponse.observeEvent(viewLifecycleOwner) { state ->
            handleResponseProductSearch(state)
        }

    }

    fun handleResponseProductSearch(state: ViewState) {

        binding.clBarraCantidadResultados.visibility =
            if (productDashboardViewModel.searchResults.results.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.tvCantidadBusqueda.text =
            "${productDashboardViewModel.searchResults.results.size} resultados"

        (binding.rvProductos.adapter as ProductDashboardAdapter)
            .setData(productDashboardViewModel.searchResults.results)

        when (state) {
            ViewState.CARGANDO -> {
                showProgressBar()
            }
            ViewState.EXITO -> {
                hideProgressBar()
                if (productDashboardViewModel.searchResults.results.isNullOrEmpty()) {
                    binding.tvCantidadBusqueda.text = "SIN RESULTADOS"
                    binding.clSinResultados.visibility = View.VISIBLE
                    binding.clResultadoBusqueda.visibility = View.GONE
                } else {
                    binding.clSinResultados.visibility = View.GONE
                    binding.clResultadoBusqueda.visibility = View.VISIBLE
                }
                binding.clError.visibility = View.GONE
                binding.clSinConexion.visibility = View.GONE
            }
            ViewState.NO_AUTENTICADO -> {
                hideProgressBar()
                binding.clResultadoBusqueda.visibility = View.GONE
                binding.clError.visibility = View.GONE
                binding.clSinConexion.visibility = View.GONE
                binding.clSinResultados.visibility = View.GONE
                showSnackBar("NO AUTENTICADO")
            }
            ViewState.ERROR -> {
                hideProgressBar()
                binding.clResultadoBusqueda.visibility = View.GONE
                binding.clError.visibility = View.VISIBLE
                binding.clSinConexion.visibility = View.GONE
                binding.clSinResultados.visibility = View.GONE
                showSnackBar("ERROR")
            }
            ViewState.SIN_CONEXION_INTERNET -> {
                hideProgressBar()
                binding.clResultadoBusqueda.visibility = View.GONE
                binding.clError.visibility = View.GONE
                binding.clSinConexion.visibility = View.VISIBLE
                binding.clSinResultados.visibility = View.GONE
                showSnackBar("SIN CONEXION")
            }
            else -> {
                Log.e(AppConstants.ETAG_RESPONSE_HANDLING_EVENT, "Estado no manejado")
            }
        }
    }

    private fun showProgressBar() {
        (activity as MainActivity).showProgressBar()
    }

    private fun hideProgressBar() {
        (activity as MainActivity).hideProgressBar()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            this.requireView(),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
