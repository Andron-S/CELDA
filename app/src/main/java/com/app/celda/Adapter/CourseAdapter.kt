package com.app.celda.Adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.celda.Model.Course
import com.app.celda.R
import kotlinx.android.synthetic.main.listitem_course.view.*

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    private val list = mutableListOf<Course>()

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.listitem_course, parent, false)
    ) {
      fun bind(course : Course) = with(itemView) {
          imgPreview.setImageBitmap(course.img)
          nameCourse.setText(course.name)
          descriptionCourse.setText(course.description)
          authorCourse.setText(course.author)
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun addItem(course: Course) {
        list.add(course)
        notifyItemInserted(list.lastIndex)
        Log.i("AUF:", "${list.size}")
    }

}