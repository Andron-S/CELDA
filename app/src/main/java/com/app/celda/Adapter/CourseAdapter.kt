package com.app.celda.Adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.celda.Model.CourseModel
import com.app.celda.Model.CourseProgress
import com.app.celda.Post.APIService
import com.app.celda.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.listitem_course.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class CourseAdapter(private val listener : Listener) : RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    private val list = mutableListOf<CourseModel>()
    private val list_progress = mutableListOf<CourseProgress>()

    interface Listener {
        fun onItemClick(id: Int)
    }

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.listitem_course, parent, false)
    ) {
        fun bind(course : CourseModel, progress: CourseProgress) = with(itemView) {
            Glide.with(context).load("http://mitiusxa.beget.tech${course.img}").into(imgPreview) // добавить placeholder после load
            nameCourse.text = course.name
            authorCourse.text = course.author

            if (progress.is_course_done == "null") {
                tvProgressCourse.text = ""
                ivCourseProgressIcon.setImageResource(R.drawable.net_popdiska)
            } else if (progress.is_course_done == "false") {
                tvProgressCourse.text = ""
                ivCourseProgressIcon.setImageResource(R.drawable.tilde_process)
            } else {
                tvProgressCourse.text = ""
                ivCourseProgressIcon.setImageResource(R.drawable.done_module)
            }

            setOnClickListener { listener.onItemClick(course.id) }
            setOnLongClickListener(object : View.OnLongClickListener{
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onLongClick(p0: View?): Boolean {
                    clItemCourse?.background = context.getDrawable(R.drawable.long_click_of_course)
                    return false
                }
            })
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun backArrow() = with(itemView) {
            clItemCourse?.background = context.getDrawable(R.drawable.course_shadow)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position], list_progress[position])
    }

    override fun getItemCount() = list.size

    fun clearList() = list.clear()

    fun addItem(course : CourseModel) {
        list.add(course)
        notifyItemInserted(list.lastIndex)
    }

    fun addProgress(progress: CourseProgress) {
        list_progress.add(progress)
        notifyItemInserted(list_progress.lastIndex)
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)



}