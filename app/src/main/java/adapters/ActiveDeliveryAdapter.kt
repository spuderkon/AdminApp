package adapters

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListActiveDeliveryItemBinding
import data.models.Delivery

class ActiveDeliveryAdapter(): RecyclerView.Adapter<ActiveDeliveryAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var selectedPosition: Int = -1
    private var selectedDelivery: Delivery? = null
    private lateinit var items: MutableList<Delivery>

    companion object Factory{

        fun create(): ActiveDeliveryAdapter{
            return ActiveDeliveryAdapter()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListActiveDeliveryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvOrderId.text = item.orderId.toString()
        holder.tvOrderAddress.text = item.order?.address?.address
        holder.tvDateArrive.text = item.dateArrive
        holder.tvCourier.text = item.user?.getShortName()
        holder.tvTotalPrice.text = item.order?.totalPrice.toString()

        holder.itemView.setOnClickListener {
            onClickListener!!.onClick(position, item )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getSelectedDelivery(): Delivery?{
        return selectedDelivery
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Delivery)
    }

    class ViewHolder(binding: ListActiveDeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that
        // will add each item to
        var tvOrderId = binding.tvOrderId
        var tvOrderAddress = binding.tvOrderAddress
        var tvDateArrive = binding.tvDateArrive
        var tvCourier = binding.tvCourier
        var tvTotalPrice = binding.tvTotalPrice
    }

    fun refreshDelivers(items: MutableList<Delivery>){
        this.items = items
    }

}