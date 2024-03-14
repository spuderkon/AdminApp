package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListOrderItemBinding
import data.models.Order


class OrderAdapter() : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    private lateinit var items: List<Order>

    companion object Factory{

        fun create(): OrderAdapter{
            return OrderAdapter()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListOrderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.id.toString()
        holder.tvOrderDate.text = item.orderDate
        // Finally add an onclickListener to the item.
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Order)
    }

    class ViewHolder(binding: ListOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that
        // will add each item to
        val tvTitle = binding.oTitle
        val tvOrderDate = binding.orderDate
    }

    fun refreshItems(items: List<Order>){
        this.items = items
    }
}
