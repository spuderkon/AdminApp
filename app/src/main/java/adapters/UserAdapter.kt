package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListUsersItemBinding
import data.models.User

class UserAdapter(): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var selectedPosition: Int = -1
    private var selectedUser: User? = null
    private lateinit var items: MutableList<User>

    companion object Factory{

        fun create(): UserAdapter{
            return  UserAdapter()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListUsersItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvFullName.text = item.getFullName()
        holder.tvRole.text = item.role?.name

        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition);
            selectedPosition =  holder.layoutPosition;
            notifyItemChanged(selectedPosition);
            selectedUser = item
            onClickListener!!.onClick(position, item )
        }
        holder.itemView.isSelected = selectedPosition == holder.layoutPosition
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getSelectedUser(): User?{
        return selectedUser
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: User)
    }

    class ViewHolder(binding: ListUsersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Holds the TextView that
        // will add each item to
        val tvFullName = binding.tvFullName
        val tvRole = binding.tvRole
    }

    fun refreshUsers(items: MutableList<User>){
        this.items = items
    }

}