package com.gousto.philip.arnold.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gousto.philip.arnold.R
import com.gousto.philip.arnold.databinding.DetailFragmentBinding
import com.gousto.philip.arnold.utils.getDisplayWidth
import com.gousto.philip.arnold.utils.nonNullObserve
import kotlinx.android.synthetic.main.item_product.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    companion object {
        fun newInstance(id: String): DetailFragment {
            val frag = DetailFragment()
            val args = Bundle()
            args.putString("id", id)
            frag.arguments = args
            return frag
        }
    }

    val viewModel: DetailViewModel by viewModel()
    lateinit var binding: DetailFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("id")?.let { item ->
            with (viewModel) {
                id.value = item
                loadData()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.detail_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.data.nonNullObserve(this) { data ->
            with (binding) {
                title.text = data.title
                if (data.list_price != "0.00") {
                    price.text = getString(R.string.listPrice, data.list_price)
                }
                description.text = data.description

                data.images?.a200?.src?.let { src ->
                    val screenWidth = requireActivity().window.getDisplayWidth()
                    val scaledWidth = screenWidth.toFloat() * 0.8
                    val newWidth = (scaledWidth / 100).toInt() * 100
                    val newUrl = src.replace(
                        "x200.jpg", "x$newWidth.jpg"
                    )
                    // Load images using Glide library
                    Glide
                        .with(requireContext())
                        .load(newUrl)
                        .fitCenter()
                        .into(image)
                    image.visibility = View.VISIBLE
                } ?: run {
                    image.visibility = View.GONE
                }

            }
        }
    }

}