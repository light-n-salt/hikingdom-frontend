package com.example.hikingdom.ui.main.hiking.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.hiking.Meetup

class MeetupAdapter(val context: Context, val dataset: List<Meetup>) :
    RecyclerView.Adapter<MeetupAdapter.ViewHolder>() {

   private lateinit var itemClickListener: OnItemClickListener    // setItemClickListener로 설정한 함수 실행
    interface OnItemClickListener{
        fun onClick(v:View,position:Int)
    } //onClick 함수는 클릭시 발생시킬 이벤트를 작성하는 함수로 외부에서 오버라이드 해줘야하는 함수

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }    //외부에서 클릭 시 이벤트 설정



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val meetupName = view.findViewById<TextView>(R.id.meetup_name)
        val mountainName = view.findViewById<TextView>(R.id.mountain_name)
        val totalMember = view.findViewById<TextView>(R.id.total_member)
        val startAt = view.findViewById<TextView>(R.id.start_at)

        fun bind(meetup: Meetup) {
            meetupName.text = meetup.meetupName
            mountainName.text = meetup.mountainName
            totalMember.text = meetup.totalMember.toString()
            startAt.text = meetup.startAt
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetupAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_select_meetup, parent, false)

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