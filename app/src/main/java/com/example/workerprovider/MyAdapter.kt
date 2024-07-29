package com.example.workerprovider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(context: Context, private val gridItems: List<Occupation>)
    : ArrayAdapter<Occupation>(context,0,gridItems){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val holder : ViewHolder

        if(convertView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.grid_items,parent,false)
            holder = ViewHolder()
            holder.textView = itemView.findViewById(R.id.textView)
            holder.imageView = itemView.findViewById(R.id.imageView)

            itemView.tag = holder
        }else{
            holder = itemView?.tag as ViewHolder
        }
        val currentItem = gridItems[position]
        holder.imageView.setImageResource(currentItem.image)
        holder.textView.text=currentItem.occupation
        return itemView!!
    }

    private class ViewHolder{
        lateinit var imageView: ImageView
        lateinit var textView: TextView
    }
}