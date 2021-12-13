package com.app.celda.Adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.celda.Model.Course
import com.app.celda.Model.Module
import com.app.celda.R
import kotlinx.android.synthetic.main.listitem_course.*
import kotlinx.android.synthetic.main.listitem_course.view.*

class CourseAdapter(private val listener : Listener) : RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    private val list = mutableListOf<Course>()
    private var listModule = mutableListOf<Module>()

    interface Listener {
        fun onItemClick(module : Module)
    }

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.listitem_course, parent, false)
    ) {
        fun bind(course : Course, module: Module) = with(itemView) {
          imgPreview.setImageBitmap(course.img)
          nameCourse.text = course.name
          descriptionCourse.text = course.description
          authorCourse.text = course.author

          setOnClickListener { listener.onItemClick(module) }
          setOnLongClickListener(object : View.OnLongClickListener{
              @SuppressLint("UseCompatLoadingForDrawables")
              override fun onLongClick(p0: View?): Boolean {
                  clItemCourse.background = context.getDrawable(R.drawable.long_click_of_course)
                  return false
              }
          })
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun backArrow() = with(itemView) {
            clItemCourse.background = context.getDrawable(R.drawable.course_shadow)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position], listModule[position])
    }

    override fun getItemCount() = list.size

    fun addItem(course: Course) {
        list.add(course)
        notifyItemInserted(list.lastIndex)
//        Log.i("AUF:", "${list.size}")
    }

    fun addItem(moduleList : MutableList<Module>) {
        listModule = moduleList
        notifyItemInserted(listModule.lastIndex)
    }

}