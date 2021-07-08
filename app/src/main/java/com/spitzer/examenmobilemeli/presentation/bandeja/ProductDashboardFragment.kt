package com.spitzer.examenmobilemeli.presentation.bandeja

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.data.Product
import com.spitzer.examenmobilemeli.databinding.FragmentDashboardProductsBinding
import com.spitzer.examenmobilemeli.models.SearchHistory
import com.spitzer.examenmobilemeli.utils.AppConstants
import com.spitzer.examenmobilemeli.utils.AppConstants.GLOBAL_SHARED_PREFERENCES
import com.spitzer.examenmobilemeli.utils.observeEvent
import com.spitzer.network.Estado

class ProductDashboardFragment : Fragment() {

    private var _binding: FragmentDashboardProductsBinding? = null
    private val binding get() = _binding!!

    private val gSON = GsonBuilder().create()
    private lateinit var productDashboardViewModel: ProductDashboardViewModel
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel
    private lateinit var productDashboardAdapter: ProductDashboardAdapter
    private lateinit var progressBar: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDashboardViewModel = ViewModelProvider(this, ProductDashboardViewModelFactory()).get(
            ProductDashboardViewModel::class.java
        )
        searchHistoryViewModel =
            ViewModelProvider(requireActivity()).get(SearchHistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardProductsBinding.inflate(inflater, container, false)
        progressBar = requireActivity().findViewById(R.id.clProgressBar) as ConstraintLayout
        searchHistoryViewModel.searchHistory = getSearchHistory()
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

        if (binding.rvProductos.adapter == null) {
            binding.rvProductos.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = productDashboardAdapter
            }
        }

        binding.clBuscador.setOnClickListener {
            val action =
                ProductDashboardFragmentDirections.actionProductDashboardFragmentToSearchFragment(
                    searchHistoryViewModel.searchHistory
                )
            findNavController().navigate(action)
        }

        binding.etSearch.text = productDashboardViewModel.searchText
        binding.clBarraCantidadResultados.visibility =
            if (productDashboardViewModel.searchResults.results.isNotEmpty()) View.VISIBLE else View.GONE
        binding.tvCantidadBusqueda.text =
            "${productDashboardViewModel.searchResults.results.size} resultados"
    }

    private fun defineObservables() {

        searchHistoryViewModel.search.observeEvent(viewLifecycleOwner) { searchText ->
            binding.etSearch.text = searchText
            if (searchText.isNotBlank()) {
                if (searchHistoryViewModel.searchHistory.busqueda_string.none { historyString -> historyString == searchText }) {
                    searchHistoryViewModel.searchHistory.busqueda_string.add(searchText)
                    updateSearchHistory(searchHistoryViewModel.searchHistory)
                }
                binding.clBarraCantidadResultados.visibility = View.GONE
                productDashboardViewModel.searchProducts(searchText)
            }
        }

        productDashboardViewModel.searchResponse.observeEvent(viewLifecycleOwner) { state ->
            handleResponseProductSearch(state)
        }

    }

    fun handleResponseProductSearch(estado: Estado) {

        binding.clBarraCantidadResultados.visibility =
            if (productDashboardViewModel.searchResults.results.isNotEmpty()) View.VISIBLE else View.GONE
        binding.tvCantidadBusqueda.text =
            "${productDashboardViewModel.searchResults.results.size} resultados"

        (binding.rvProductos.adapter as ProductDashboardAdapter)
            .setData(productDashboardViewModel.searchResults.results)

        when (estado) {
            Estado.CARGANDO -> {
                showProgressBar()
            }
            Estado.EXITO -> {
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
            Estado.NO_AUTENTICADO -> {
                hideProgressBar()
                binding.clResultadoBusqueda.visibility = View.GONE
                binding.clError.visibility = View.GONE
                binding.clSinConexion.visibility = View.GONE
                binding.clSinResultados.visibility = View.GONE
                Snackbar.make(this.requireView(), "NO AUTENTICADO", Snackbar.LENGTH_SHORT).show()
            }
            Estado.ERROR -> {
                hideProgressBar()
                binding.clResultadoBusqueda.visibility = View.GONE
                binding.clError.visibility = View.VISIBLE
                binding.clSinConexion.visibility = View.GONE
                binding.clSinResultados.visibility = View.GONE
                Snackbar.make(this.requireView(), "ERROR", Snackbar.LENGTH_LONG).show()
            }
            Estado.SIN_CONEXION_INTERNET -> {
                hideProgressBar()
                binding.clResultadoBusqueda.visibility = View.GONE
                binding.clError.visibility = View.GONE
                binding.clSinConexion.visibility = View.VISIBLE
                binding.clSinResultados.visibility = View.GONE
                Snackbar.make(this.requireView(), "SIN CONEXION", Snackbar.LENGTH_LONG).show()
            }
            else -> {
                Snackbar.make(this.requireView(), "ESTADO NO MANEJADO", Snackbar.LENGTH_LONG).show()
                Log.e(AppConstants.ETAG_RESPONSE_HANDLING_EVENT, "Estado no manejado")
            }
        }
    }

    private fun showProgressBar() {
        this.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        this.progressBar.visibility = View.GONE
    }

    // TODO: 8/3/2020 el manejo de las Preferences podría implementarse el repositorio del ViewModel inyectandole el application
    // TODO: 8/4/2020 los historiales podrían guardar consigo la fecha de inserción para usar como parámetro de ordenamiento 
    private fun getSearchHistory(): SearchHistory {
        val preferences = this.requireActivity()
            .getSharedPreferences(GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            ?: return SearchHistory()
        val serializedHistory: String
        return try {
            serializedHistory =
                preferences.getString(AppConstants.SEARCH_HISTORY_KEY, "") ?: ""
            gSON.fromJson(serializedHistory, SearchHistory::class.java)
        } catch (e: Exception) {
            Log.e(
                AppConstants.ETAG_SHARED_PREFERENCES,
                e.localizedMessage
                    ?: "Excepcion al obtener el historial de búsqueda en Shared Preferences Globales."
            )
            SearchHistory()
        }
    }

    private fun updateSearchHistory(searchHistory: SearchHistory): Boolean {
        val serializedHistory = gSON.toJson(searchHistory)
        val preferences = this.requireActivity()
            .getSharedPreferences(GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE) ?: return false
        return try {
            with(preferences.edit()) {
                putString(AppConstants.SEARCH_HISTORY_KEY, serializedHistory)
                commit()
            }
            true
        } catch (e: Exception) {
            Log.e(
                AppConstants.ETAG_SHARED_PREFERENCES,
                e.localizedMessage
                    ?: "Excepcion al actualizar el historial de búsqueda en Shared Preferences Globales."
            )
            false
        }
    }
}
