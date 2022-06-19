package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.celda.Adapter.CourseAdapter
import com.app.celda.Model.CourseModel
import com.app.celda.Model.CourseProgress
import com.app.celda.Post.APIService
import com.app.celda.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.main_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit

class HomeFragment: Fragment(), CourseAdapter.Listener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.home_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private var adapter : CourseAdapter? = null

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    private fun initialization() {
        adapter = CourseAdapter(this)
        rcCourses?.adapter = adapter
        rcCourses?.layoutManager = LinearLayoutManager(requireContext())
        get()

    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireContext(), CourseScreen::class.java)
        intent.putExtra("key", "$id")
        startActivity(intent)
//        this.finish()
    }

    fun get() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCourses()
            val response2 = service.getProgressCourse()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    val prettyJson2 = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    val courses = JSONObject(prettyJson).get("courses")

                    val all = Gson().fromJson<List<CourseProgress>>(prettyJson2)
                    all.forEach {adapter?.addProgress(it)}
                    Log.d("Pretty Printed JSON :", prettyJson)
                    val lst = Gson().fromJson<List<CourseModel>>(prettyJson)
                    lst?.forEach { adapter?.addItem(it) }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }

        }
    }




}

// http://mitiusxa.beget.tech/courses/