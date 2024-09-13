package uz.abubakir_khakimov.pubtrans_finder.features.home.adapters

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.core.view.setPadding
import uz.abubakir_khakimov.pubtrans_finder.R
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.px
import uz.abubakir_khakimov.pubtrans_finder.databinding.PubTransTypesSelectorItemLayoutBinding
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransType

class PubTransTypesAdapter(context: Context) : ArrayAdapter<PubTransType>(
    /* context = */ context,
    /* resource = */ 0
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(/* context = */ parent.context)
            .inflate(
                /* resource = */ R.layout.pub_trans_types_selector_item_layout,
                /* root = */ parent, /* attachToRoot = */ false
            )
        val binding = PubTransTypesSelectorItemLayoutBinding.bind(/* view = */ view)

        val item = getItem(/* position = */ position) ?: return view

        binding.icon.setImageResource(/* resId = */ item.icon)
        binding.title.text = item.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(position, convertView, parent).apply { setPadding(8.px) }

    fun submitList(list: List<PubTransType>) {
        clear()
        addAll(/* collection = */ list)
    }
}