import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restapigetpostbackent.databinding.ItemRvBinding
import com.example.restapigetpostbackent.models.Contacts
import com.example.restapigetpostbackent.models.Todo

class myAdapter(private val itemList: List<Contacts>) :
    RecyclerView.Adapter<myAdapter.MyViewHolder>() {

    inner class MyViewHolder( val binding: ItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todo = itemList[position]
        holder.binding.tvName.text=todo.name
        holder.binding.tvNumber.text=todo.phone
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
