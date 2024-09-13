package uz.abubakir_khakimov.pubtrans_finder.features.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.abubakir_khakimov.pubtrans_finder.R
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.paintDefinitePartOfText
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.paintDefinitePartOfTextLast
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.px
import uz.abubakir_khakimov.pubtrans_finder.databinding.PubTransStationsItemLayoutBinding
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransStation

class PubTransStationsAdapter : ListAdapter<PubTransStation, PubTransStationsAdapter.ItemHolder>(PubTransStationDiffItemCallback()) {

    inner class ItemHolder(
        private val binding: PubTransStationsItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(pubTransStation: PubTransStation, position: Int) {
            binding.root.layoutParams = getParams(position = position)
            binding.name.text = pubTransStation.name
            binding.walkTime.setText(
                /* text = */ pubTransStation.walkTime,
                /* type = */ TextView.BufferType.SPANNABLE
            )
            binding.distance.text = pubTransStation.distance
            binding.transportNumber.text = pubTransStation.transportNumber
            binding.direction.text = pubTransStation.direction
            binding.times.text = pubTransStation.times

            binding.walkTime.paintDefinitePartOfText(
                partOfText = pubTransStation.walkTime.let { str ->
                    str.substring(startIndex = 0, endIndex = str.indexOf(string = " "))
                },
                colorRes = R.color.blue
            )
        }

        private fun getParams(position: Int): ViewGroup.LayoutParams = LinearLayout
            .LayoutParams(
                /* width = */ LinearLayout.LayoutParams.MATCH_PARENT,
                /* height = */ LinearLayout.LayoutParams.WRAP_CONTENT
            ).also { params ->
                if (position == 0) {
                    params.topMargin = 12.px
                } else if (position == itemCount - 1) {
                    params.bottomMargin = 12.px
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            PubTransStationsItemLayoutBinding.inflate(
                /* inflater = */ LayoutInflater.from(/* context = */ parent.context),
                /* parent = */parent,
                /* attachToParent = */false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setItem(
            pubTransStation = getItem(/* position = */ position)!!,
            position = position
        )
    }
}

class PubTransStationDiffItemCallback : DiffUtil.ItemCallback<PubTransStation>() {

    override fun areItemsTheSame(oldItem: PubTransStation, newItem: PubTransStation): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PubTransStation, newItem: PubTransStation): Boolean =
        oldItem == newItem
}