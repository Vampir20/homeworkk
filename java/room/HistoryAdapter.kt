package room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.countdown.R
import com.example.countdown.RecyclerItemViewHolder

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder> {
    private var data: List<DataModel> = arrayListOf<>()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) {
        RecyclerItemViewholder {
            return RecyclerItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_history_recycler_view_item, parent, false) as View
            )
        }
        override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class RecyclerItemViewHolder(view: View): RecyclerView.ViewHolder(view){
            fun bind(data: DataModel){
                if (layoutPosition != RecyclerView.NO_POSITION){
                    itemView.header_history_text_view_recycler_item.text = data.text
                    itemView.setOnClickListener{
                        Toast.makeText(itemView.context, "on click ${data.text}",
                        Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

}