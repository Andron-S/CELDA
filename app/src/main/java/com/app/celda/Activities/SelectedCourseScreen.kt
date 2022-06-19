package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.app.celda.Adapter.CourseAdapter
import com.app.celda.Adapter.ExpandableListAdapter
import com.app.celda.Model.*
import com.app.celda.Post.APIService
import com.app.celda.R
import com.app.celda.TokenManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.course_screen.*
import kotlinx.android.synthetic.main.module_group.*
import kotlinx.android.synthetic.main.selected_course_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit

class SelectedCourseScreen : AppCompatActivity() {

    lateinit var listDataChild : List<List<LessonModel>>
    lateinit var listDataHeader : List<ModulesModel>
    lateinit var anime : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_course_layout)

        //val past_intent = intent.getStringExtra("key")

//        val module = intent.getStringExtra("movie")
//        Log.i("EXPANDABLE:","$module")
       // prepareListData()
        //get(past_intent!!.toInt())

        lvExpnd.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener {
            override fun onGroupClick(
                p0: ExpandableListView?,
                p1: View?,
                p2: Int,
                p3: Long
            ): Boolean {
                return false
            }
        })

        lvExpnd.setOnGroupExpandListener(object : ExpandableListView.OnGroupExpandListener {
            override fun onGroupExpand(p0: Int) {
            }
        })

        lvExpnd.setOnGroupCollapseListener(object : ExpandableListView.OnGroupCollapseListener {
            override fun onGroupCollapse(p0: Int) {
            }
        })

        lvExpnd.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
            override fun onChildClick(
                p0: ExpandableListView?,
                p1: View?,
                p2: Int,
                p3: Int,
                p4: Long
            ): Boolean {

                put(listDataChild[p2][p3].id_lesson)
                val i = Intent(this@SelectedCourseScreen, LectureActivity::class.java)
                i.putExtra("key","${listDataChild[p2][p3].id_lesson}")
                startActivity(i)


                //Toast.makeText(applicationContext, "Tik", Toast.LENGTH_SHORT).show()
                return false
            }


        })

    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext,"onPause",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext,"onResume",Toast.LENGTH_SHORT).show()
        val past_intent = intent.getStringExtra("key")
        get(past_intent!!.toInt())
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun get(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getModules(id)
            val responseIsDone = service.getDone(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful && responseIsDone.isSuccessful) {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    val prettyJson2 = gson.toJson(
                        JsonParser.parseString(
                            responseIsDone.body()
                                ?.string()
                        )
                    )

                    val modules = JSONObject(prettyJson).get("modules")
                    val lessons = JSONObject(prettyJson).get("lessons")
                    val modulesDone = JSONObject(prettyJson2).get("modules")
                    val lessonsDone = JSONObject(prettyJson2).get("lessons")

                    val listModulesDone = Gson().fromJson<List<ModuleDoneModel>>(modulesDone.toString())
                    val listLessonDone = Gson().fromJson<List<LessonDoneModel>>(lessonsDone.toString())

                    listDataHeader = Gson().fromJson<List<ModulesModel>>(modules.toString())
                    listDataChild = Gson().fromJson<List<List<LessonModel>>>(lessons.toString())

                    lvExpnd.setAdapter(ExpandableListAdapter(applicationContext,listDataHeader, listDataChild, listModulesDone, listLessonDone))

                    Log.i("INFO MODULES::", "$listDataHeader")
                    Log.i("INFO LESSONS::", "$listDataChild")

                     Log.d("Pretty Printed JSON :", prettyJson)
                    Log.d("Pretty2::", prettyJson2)
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }

        }
    }

    fun put(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.putProgressLesson(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )


                    Log.d("Pretty Printed JSON :", prettyJson)
                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }

        }
    }



}