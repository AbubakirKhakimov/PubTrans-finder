package uz.abubakir_khakimov.pubtrans_finder.features.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.setPadding
import uz.abubakir_khakimov.pubtrans_finder.R
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.px
import uz.abubakir_khakimov.pubtrans_finder.databinding.DistancesSelectorItemLayoutBinding

class DistancesAdapter(context: Context) : ArrayAdapter<String>(
    /* context = */ context,
    /* resource = */ 0
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(/* context = */ parent.context)
            .inflate(
                /* resource = */ R.layout.distances_selector_item_layout,
                /* root = */ parent, /* attachToRoot = */ false
            )
        val binding = DistancesSelectorItemLayoutBinding.bind(/* view = */ view)

        binding.root.text = getItem(/* position = */ position) as String

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(/* position = */ position, convertView, parent).apply {
            setPadding(/* left = */ 16.px,/* top = */ 8.px,/* right = */ 16.px, /* bottom = */ 8.px)
        }

    fun submitList(list: List<String>) {
        clear()
        addAll(/* collection = */ list)
    }
}