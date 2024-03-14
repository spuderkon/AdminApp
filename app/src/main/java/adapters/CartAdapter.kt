package adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListCartItemBinding
import data.models.Cart
import data.models.Delivery


class CartAdapter() : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    /*private var onClickListener: OnClickListener? = null*/
    private lateinit var _context: Context
    private lateinit var items: List<Cart>

    companion object Factory{

        fun create(context: Context): CartAdapter{
            val x = CartAdapter()
            x._context = context
            return x
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListCartItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvCartImage.setImageResource(_context.resources.getIdentifier(item.product!!.image, "drawable", _context.packageName))
        holder.tvCartName.text = item.product!!.name
        holder.amount.text = item.amount.toString()
        holder.price.text = (item.amount!! * item.product!!.price).toString()
        // Finally add an onclickListener to the item.
        /*holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }*/
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /*fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Cart)
    }*/

    class ViewHolder(binding: ListCartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that
        // will add each item to
        val tvCartImage = binding.cartImage
        val tvCartName = binding.cartName
        val amount = binding.amount
        val price = binding.price
    }

    fun refreshCarts(items: List<Cart>){
        this.items = items
    }
}