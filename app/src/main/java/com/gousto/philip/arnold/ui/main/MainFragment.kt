package com.gousto.philip.arnold.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gousto.philip.arnold.R
import com.gousto.philip.arnold.databinding.MainFragmentBinding
import com.gousto.philip.arnold.storage.StoredData
import com.gousto.philip.arnold.utils.getDisplayWidth
import com.gousto.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment() : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    var activityLiveData: MutableLiveData<String>? = null

    val viewModel: MainViewModel by viewModel()
    var mainAdapter: MainAdapter? = null
    lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            displayProducts
                .nonNullObserve(this@MainFragment) { products ->
                    mainAdapter?.updateData(products.data)
                }
            showError.nonNullObserve(viewLifecycleOwner) { showError ->
                Toast.makeText(requireContext(), showError, Toast.LENGTH_SHORT).show()
            }
            showLoading.nonNullObserve(viewLifecycleOwner) { showLoading ->
                binding.mainProgressBar.visibility =
                    if (showLoading)
                        View.VISIBLE
                    else
                        View.GONE
            }
            navigateToDetail.nonNullObserve(viewLifecycleOwner) { item ->
                activityLiveData?.value = item
            }
        }

        with (binding) {
            clearButton.setOnClickListener {
                if (search.text.isNotEmpty()) {
                    search.text.clear()
                    viewModel.loadAllProducts(::scrollToTop)
                }
                dismissKeyboard()
            }
            searchButton.setOnClickListener {
                if (search.text.isNotEmpty()) {
                    viewModel.filterProducts(search.text.toString(), ::scrollToTop)
                    dismissKeyboard()
                }
            }
        }

        val width = requireActivity().window.getDisplayWidth()
        val onItemClicked: (item: StoredData) -> Unit = { item ->
            item.id?.let { id ->
                navigateToItem(id)
            }
        }
        // Instantiate our custom Adapter with the click listener
        mainAdapter = MainAdapter(width, onItemClicked)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    }

    fun scrollToTop() {
        binding.recycler.scrollToPosition(0)
    }

    private fun MainFragmentBinding.dismissKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchButton.windowToken, 0)
    }

    private fun navigateToItem(id: String) {
        val action = MainFragmentDirections.mainToDetails(id)
        findNavController().navigate(action)
    }
}
