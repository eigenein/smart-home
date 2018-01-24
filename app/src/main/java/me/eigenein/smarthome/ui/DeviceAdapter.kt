package me.eigenein.smarthome.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.eigenein.smarthome.Device
import me.eigenein.smarthome.R
import java.net.InetAddress

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.Item.ViewHolder>() {

    // FIXME: this is just to test.
    private val items: List<Item> = listOf(RGBItem(Device(InetAddress.getLoopbackAddress(), 0)))

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

    companion object {
        private val viewHolders: MutableMap<Int, (View) -> DeviceAdapter.Item.ViewHolder> = mutableMapOf()

        fun registerViewType(viewType: Int, createViewHolder: (View) -> DeviceAdapter.Item.ViewHolder) {
            viewHolders[viewType] = createViewHolder
        }
    }

    abstract class Item(device: Device) {
        abstract val itemViewType: Int

        abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            abstract fun bind(item: Item)
        }
    }
}
