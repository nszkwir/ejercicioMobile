package com.spitzer.examenmobilemeli.presentation.productSearch

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.spitzer.examenmobilemeli.databinding.FragmentSearchBinding
import com.spitzer.examenmobilemeli.presentation.searchhistory.SearchHistoryViewModel
import com.spitzer.examenmobilemeli.presentation.searchhistory.SearchHistoryViewModelFactory
import com.spitzer.examenmobilemeli.utils.hideKeyboard
import com.spitzer.examenmobilemeli.utils.showKeyboard

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchHistoryViewModel: SearchHistoryViewModel
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchHistoryViewModel = ViewModelProvider(
            requireActivity(),
            SearchHistoryViewModelFactory(requireContext())
        ).get(SearchHistoryViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {

        searchHistoryAdapter = SearchHistoryAdapter(searchHistoryViewModel.searchHistory)
        searchHistoryAdapter.onItemClickFunction { searchText ->
            setSearchString(searchText)
        }
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        binding.rvBusquedasPropuestas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchHistoryAdapter
            adapter?.notifyDataSetChanged()
        }

        binding.svFiltro.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryHint = "Buscar producto ..."
            setIconifiedByDefault(false)
            requestFocus()
            showKeyboard()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(searchString: String): Boolean {
                    setSearchString(searchString)
                    binding.svFiltro.hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(searchString: String): Boolean {
                    (binding.rvBusquedasPropuestas.adapter as SearchHistoryAdapter).apply {
                        filter(searchString)
                        notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
    }

    fun setSearchString(searchString: String) {
        binding.svFiltro.hideKeyboard()
        searchHistoryViewModel.setSearchString(searchString)
        findNavController().popBackStack()
    }
}
