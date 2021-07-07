package com.spitzer.examenmobilemeli.presentation.buscador

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.spitzer.examenmobilemeli.R
import com.spitzer.examenmobilemeli.databinding.FragmentBuscadorBinding
import com.spitzer.examenmobilemeli.interfaces.IClickListener
import com.spitzer.examenmobilemeli.presentation.bandeja.HistorialBusquedaViewModel
import com.spitzer.examenmobilemeli.utils.hideKeyboard
import com.spitzer.examenmobilemeli.utils.showkeyboard

class BuscadorFragment : Fragment() {
    val args: BuscadorFragmentArgs by navArgs()
    private lateinit var binding: FragmentBuscadorBinding
    private lateinit var mViewModelBusqueda: HistorialBusquedaViewModel
    private lateinit var adapter: HistorialBusquedaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buscador, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val historial = args.historialBusqueda
        mViewModelBusqueda =
            ViewModelProviders.of(requireActivity()).get(HistorialBusquedaViewModel::class.java)
        initView()
    }

    private fun initView() {

        adapter = HistorialBusquedaAdapter(
            mViewModelBusqueda.historialBusqueda,
            object : IClickListener {
                override fun onClick(v: View, index: Int) {
                    binding.svFiltro.hideKeyboard()
                    setearStringBusqueda(adapter.items[index])
                }
            }
        )

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svFiltro.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        binding.svFiltro.queryHint = "Buscar producto ..."
        binding.svFiltro.setIconifiedByDefault(false)
        binding.svFiltro.requestFocus()
        binding.svFiltro.showkeyboard()

        binding.rvBusquedasPropuestas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBusquedasPropuestas.adapter = adapter
        (binding.rvBusquedasPropuestas.adapter as HistorialBusquedaAdapter).notifyDataSetChanged()

        binding.svFiltro.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                setearStringBusqueda(s)
                binding.svFiltro.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                (binding.rvBusquedasPropuestas.adapter as HistorialBusquedaAdapter).filtrar(s)
                (binding.rvBusquedasPropuestas.adapter as HistorialBusquedaAdapter).notifyDataSetChanged()
                return true
            }
        })
//
//        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun setearStringBusqueda(stringBusqueda: String) {
        mViewModelBusqueda.setBusqueda(stringBusqueda)
        findNavController().popBackStack()
    }

}