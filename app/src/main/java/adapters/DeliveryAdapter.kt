package adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListCourierItemBinding
import com.example.myapplication.databinding.ListDeliveryItemBinding
import data.models.Delivery
import data.models.User

class DeliveryAdapter(): RecyclerView.Adapter<DeliveryAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var selectedPosition: Int = -1
    private var selectedDelivery: Delivery? = null
    private lateinit var items: MutableList<Delivery>

    companion object Factory{

        fun create(): DeliveryAdapter{
            return DeliveryAdapter()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListDeliveryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvOrderId.text = "№ ${item.orderId}"
        holder.rvDeliveryDate.text = item.dateArrive

        holder.itemView.setOnClickListener {

        }
        /*holder.tvFullName.text = item.getFullName()
        holder.tvPhoneNumber.text = item.phoneNumber

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition);
            selectedPosition =  holder.layoutPosition;
            notifyItemChanged(selectedPosition);
            onClickListener!!.onClick(position, item )
        }
        holder.itemView.isSelected = selectedPosition == holder.layoutPosition*/
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
        fun onClick(position: Int, model: User)
    }

    class ViewHolder(binding: ListDeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that
        // will add each item to
        var tvOrderId = binding.orderId
        var rvDeliveryDate = binding.deliveryDate
    }

    fun refreshDelivers(items: MutableList<Delivery>){
        this.items = items
    }

}