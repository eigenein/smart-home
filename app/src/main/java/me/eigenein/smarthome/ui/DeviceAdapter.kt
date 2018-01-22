package me.eigenein.smarthome.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import me.eigenein.smarthome.Device

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.Item.ViewHolder>() {
    companion object {
        private val viewHolders: MutableMap<Int, (View) -> DeviceAdapter.Item.ViewHolder> = mutableMapOf()

        fun registerViewType(viewType: Int, createViewHolder: (View) -> DeviceAdapter.Item.ViewHolder) {
            viewHolders[viewType] = createViewHolder
        }
    }

    private val items: List<Item> = listOf()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item.ViewHolder {
        return viewHolders.getValue(viewType)(View.inflate(parent.context, viewType, parent))
    }

    override fun onBindViewHolder(holder: Item.ViewHolder, position: Int) = holder.bind(items[position])

    abstract class Item(val device: Device) {
        abstract val itemViewType: Int

        abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            abstract fun bind(item: Item)
        }
    }
}
