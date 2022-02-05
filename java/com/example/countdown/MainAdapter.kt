package com.example.countdown

import android.view.LayoutInflater
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter (private var onListItemClickListener: AdapterView.OnItemClickListener):
        RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>(){

        }

inner class RecyclerItemViewHolder(view: LayoutInflater): RecyclerView.ViewHolder(view){

    fun bind(data: DataModel){
            if (layoutPosition != RecyclerView.NO_POSITION){
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text =
                    convertMeaningsToString(data.meanings!!)

                itemView.setOnClickListener{openInNewWindow(data)}

            }
    }

}

private fun openInNewWindow(listItemData: DataModel){
    onListItemClickListener.itemOnClick(listItemData)

}

interface onListItemClickListener{
    fun onItemClick(data: DataModel)
}