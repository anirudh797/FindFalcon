package com.anirudh.findfalcone.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.anirudh.findfalcone.data.entity.InfoItem
import com.anirudh.findfalcone.data.entity.PlanetInfo
import com.anirudh.findfalcone.data.entity.VehicleInfo
import com.anirudh.findfalcone.databinding.SpinnerItemBinding


class CustomArrayAdapter
    (context: Context, arrayList: ArrayList<InfoItem>) :
    ArrayAdapter<InfoItem?>(context, 0, arrayList as List<InfoItem>) {

    private lateinit var binding: SpinnerItemBinding

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        binding = SpinnerItemBinding.inflate(LayoutInflater.from(context))
        val currentItemView: View = convertView ?: binding.root
        val item: InfoItem? = getItem(position)
        val desc = when {
            item is PlanetInfo -> item.name
            item is VehicleInfo -> item.name
            else -> {
                ""
            }
        }
        binding.name.text = desc
        return currentItemView
    }
}