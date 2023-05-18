package com.example.hikingdom.ui.main.hiking.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.hiking.Mountain

class MountainAdapter(val context: Context, val dataset: List<Mountain>) :
    RecyclerView.Adapter<MountainAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener    // setItemClickListener로 설정한 함수 실행

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    } //onClick 함수는 클릭시 발생시킬 이벤트를 작성하는 함수로 외부에서 오버라이드 해줘야하는 함수

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }    //외부에서 클릭 시 이벤트 설정

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mountainName = view.findViewById<TextView>(R.id.mountain_name)
        val mountainAlt = view.findViewById<TextView>(R.id.mountain_alt)
        val mountainAddress = view.findViewById<TextView>(R.id.mountain_address)
        val mountainImg = view.findViewById<ImageView>(R.id.mountain_image)

        private val imageLoader = ImageLoader(context)
        private val radius = 10

        fun bind(mountain: Mountain) {
            mountainName.text = mountain.name
            mountainAlt.text = mountain.maxAlt.toString()
            mountainAddress.text = mountain.address

            val request = ImageRequest.Builder(context)
                .data(mountain.imgUrl)
                .transformations(RoundedCornersTransformation(radius.toFloat()))
                .placeholder(R.drawable.placeholder_primary)
                .target(mountainImg)
                .build()

            imageLoader.enqueue(request)
            mountainImg.setColorFilter(Color.parseColor("#50000000"), PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainAdapter.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_select_mountain, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
        // onClick 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


}