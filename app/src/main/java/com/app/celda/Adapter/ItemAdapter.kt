//package com.app.celda.Adapter
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.app.celda.Model.CourseModel
//import com.app.celda.R
//
//class ItemAdapter(var itemList: List<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
//
//    private val list = mutableListOf<CourseModel>()
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setFilteredList(filteredList: List<Item>) {
//        this.itemList = filteredList
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.listitem_course,parent,false)
//        return ItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item: Item = itemList[position]
//        holder.itemNameTv.text = item.getItemName()
//    }
//
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//
//    class ItemViewHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//            var itemNameTv: TextView = itemView.findViewById(R.id.nameCourse)
//
//    }
//
//    fun addItem(course : CourseModel) {
//        list.add(course)
//        notifyItemInserted(list.lastIndex)
//    }
//
//}