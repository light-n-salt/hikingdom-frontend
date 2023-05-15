package com.example.hikingdom.ui.main.hiking.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.hiking.Mountain

class MountainAdapter(val context: Context, val dataset:List<Mountain>): RecyclerView.Adapter<MountainAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mountainName = view.findViewById<TextView>(R.id.mountain_name)
        val mountainAlt = view.findViewById<TextView>(R.id.mountain_alt)
        val mountainAddress = view.findViewById<TextView>(R.id.mountain_address)
        val mountainImg = view.findViewById<ImageView>(R.id.mountain_image)

        fun bind(mountain: Mountain){
            mountainName.text = mountain.name
            mountainAlt.text = mountain.maxAlt.toString()
            mountainAddress.text = mountain.address
            Glide.with(context).load(mountain.imgUrl).into(mountainImg)
            mountainImg.setColorFilter(Color.parseColor("#50000000"), PorterDuff.Mode.SRC_ATOP);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_select_mountain, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


}