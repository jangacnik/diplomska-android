package com.example.diplomskanaloga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.models.DeviceViewModel

class DeviceAdapter(private val mList: List<DeviceViewModel>) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_device, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.textViewName.text = ItemsViewModel.name
        holder.textViewMac.text = ItemsViewModel.mac

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textview_device_name)
        val textViewMac: TextView = itemView.findViewById(R.id.textview_device_mac)
    }
}