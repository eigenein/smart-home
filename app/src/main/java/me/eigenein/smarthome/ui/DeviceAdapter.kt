package me.eigenein.smarthome.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.eigenein.smarthome.R
import me.eigenein.smarthome.core.Device
import me.eigenein.smarthome.core.Response

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.Item.ViewHolder>() {

    private val items = listOf<Item>()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item.ViewHolder {
        return viewHolders.getValue(viewType)(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rgb, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Item.ViewHolder, position: Int) = holder.bind(items[position])

    fun handleResponse(response: Response) {
        // TODO
    }

    companion object {
        private val viewHolders: MutableMap<Int, (View) -> DeviceAdapter.Item.ViewHolder> = mutableMapOf()

        fun registerViewType(viewType: Int, createViewHolder: (View) -> DeviceAdapter.Item.ViewHolder) {
            viewHolders[viewType] = createViewHolder
        }
    }

    abstract class Item(val device: Device) {
        abstract val itemViewType: Int

        abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            abstract fun bind(item: Item)
        }
    }
}
