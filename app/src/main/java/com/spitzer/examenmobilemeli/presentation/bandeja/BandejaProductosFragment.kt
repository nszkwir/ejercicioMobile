package com.spitzer.examenmobilemeli.presentation.bandeja

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.databinding.FragmentBandejaProductosBinding
import com.spitzer.examenmobilemeli.interfaces.IClickListener
import com.spitzer.examenmobilemeli.models.HistorialBusqueda
import com.spitzer.examenmobilemeli.utils.AppConstants
import com.spitzer.examenmobilemeli.utils.AppConstants.GLOBAL_SHARED_PREFERENCES
import com.spitzer.network.Estado


class BandejaProductosFragment : Fragment() {

    private val gSON = GsonBuilder().create()
    private lateinit var mViewModel: BandejaProductosViewModel
    private lateinit var mViewModelBusqueda: HistorialBusquedaViewModel
    private lateinit var binding: FragmentBandejaProductosBinding
    private lateinit var bandejaProductosAdapter: BandejaProductosAdapter
    private lateinit var progressBar: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this, BandejaProductosViewModelFactory()).get(
            BandejaProductosViewModel::class.java
        )
        mViewModelBusqueda =
            ViewModelProviders.of(requireActivity()).get(HistorialBusquedaViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bandeja_productos, container, false)
        progressBar = requireActivity().findViewById(R.id.clProgressBar) as ConstraintLayout
        mViewModelBusqueda.historialBusqueda = obtenerHistorialBusqueda()

        bandejaProductosAdapter = BandejaProductosAdapter(
            mViewModel.resultadoBusqueda,
            object : IClickListener {
                override fun onClick(v: View, index: Int) {
                    val action =
                        BandejaProductosFragmentDirections.actionBandejaProductosFragmentToProductoFragment(
                            (binding.rvProductos.adapter!! as BandejaProductosAdapter).getItem(index)
                        )
                    findNavController().navigate(action)
                }
            })

        definirObservables()
        definirBindings()
        return binding.root
    }

    fun definirBindings() {

        if (binding.rvProductos.adapter == null) {
            binding.rvProductos.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = bandejaProductosAdapter
            }
        }

        binding.clBuscador.setOnClickListener {
            val action =
                BandejaProductosFragmentDirections.actionBandejaProductosFragmentToBuscadorFragment(
                    mViewModelBusqueda.historialBusqueda
                )
            findNavController().navigate(action)
        }

        binding.etSearch.text = mViewModel.textoBusqueda
        binding.clBarraCantidadResultados.visibility =
            if (mViewModel.resultadoBusqueda.results.isNotEmpty()) View.VISIBLE else View.GONE
        binding.tvCantidadBusqueda.text = "${mViewModel.resultadoBusqueda.results.size} resultados"
    }

    fun definirObservables() {

        mViewModelBusqueda.busqueda.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let { textoBusqueda ->
                    binding.etSearch.text = textoBusqueda
                    if (textoBusqueda.isNotBlank()) {
                        if (mViewModelBusqueda.historialBusqueda.busqueda_string.filter { it == textoBusqueda }
                                .isEmpty()) {
                            mViewModelBusqueda.historialBusqueda.busqueda_string.add(textoBusqueda)
                            actualizarHistorialBusqueda(mViewModelBusqueda.historialBusqueda)
                        }
                        binding.clBarraCantidadResultados.visibility = View.GONE
                        mViewModel.buscarProductos(textoBusqueda)
                    }
                }
            })

        mViewModel.respuestaProductos.observe(
            viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { estado ->
                    handleResponseBusquedaProducto(estado)
                }
            })
    }

    fun handleResponseBusquedaProducto(estado: Estado) {

        binding.clBarraCantidadResultados.visibility =
            if (mViewModel.resultadoBusqueda.results.isNotEmpty()) View.VISIBLE else View.GONE
        binding.tvCantidadBusqueda.text = "${mViewModel.resultadoBusqueda.results.size} resultados"

        (binding.rvProductos.adapter as BandejaProductosAdapter).setData(mViewModel.resultadoBusqueda.results)
        when (estado) {
            Estado.CARGANDO -> {
                showProgressBar()
            }
            Estado.EXITO -> {
                hideProgressBar()
                if (mViewModel.resultadoBusqueda.results.isNullOrEmpty()) {
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

    fun showProgressBar() {
        this.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        this.progressBar.visibility = View.GONE
    }

    // TODO: 8/3/2020 el manejo de las Preferences podría implementarse el repositorio del ViewModel inyectandole el application
    // TODO: 8/4/2020 los historiales podrían guardar consigo la fecha de inserción para usar como parámetro de ordenamiento 
    private fun obtenerHistorialBusqueda(): HistorialBusqueda {
        val preferences = this.requireActivity()
            .getSharedPreferences(GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            ?: return HistorialBusqueda()
        var historialSerializado: String
        return try {
            historialSerializado =
                preferences.getString(AppConstants.HISTORIAL_BUSQUEDA_KEY, "") ?: ""
            gSON.fromJson(historialSerializado, HistorialBusqueda::class.java)
        } catch (e: Exception) {
            Log.e(
                AppConstants.ETAG_SHARED_PREFERENCES,
                e.localizedMessage
                    ?: "Excepcion al obtener el historial de búsqueda en Shared Preferences Globales."
            )
            HistorialBusqueda()
        }
    }

    private fun actualizarHistorialBusqueda(historial: HistorialBusqueda): Boolean {
        val historialSerializado = gSON.toJson(historial)
        val preferences = this.requireActivity()
            .getSharedPreferences(GLOBAL_SHARED_PREFERENCES, Context.MODE_PRIVATE) ?: return false
        return try {
            with(preferences.edit()) {
                putString(AppConstants.HISTORIAL_BUSQUEDA_KEY, historialSerializado)
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
