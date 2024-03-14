package adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListCourierItemBinding
import data.models.User

class CourierAdapter(): RecyclerView.Adapter<CourierAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var selectedPosition: Int = -1
    private var selectedCourier: User? = null
    private lateinit var items: List<User>

    companion object Factory{

        fun create(): CourierAdapter{
            return  CourierAdapter()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListCourierItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvFullName.text = item.getFullName()
        holder.tvPhoneNumber.text = item.phoneNumber

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition);
            selectedPosition =  holder.layoutPosition;
            notifyItemChanged(selectedPosition);
            selectedCourier = item
            onClickListener!!.onClick(position, item )
        }
        holder.itemView.isSelected = selectedPosition == holder.layoutPosition
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getSelectedCourier(): User?{
        return selectedCourier
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: User)
    }

    class ViewHolder(binding: ListCourierItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that
        // will add each item to
        val tvFullName = binding.courierFullName
        val tvPhoneNumber = binding.phoneNumber
    }

    fun refreshCouriers(items: List<User>){
        this.items = items
    }

}