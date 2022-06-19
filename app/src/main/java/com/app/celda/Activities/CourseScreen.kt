package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.celda.Model.CourseInfo
import com.app.celda.Model.CourseModel
import com.app.celda.Post.APIService
import com.app.celda.R
import com.app.celda.TokenManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.course_screen.*
import kotlinx.android.synthetic.main.course_screen.view.*
import kotlinx.android.synthetic.main.listitem_course.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class CourseScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_screen)
        val past_intent = intent.getStringExtra("key")
        get2(past_intent!!.toInt())
        get(past_intent!!.toInt())

        courseBtn.setOnClickListener {
            post(past_intent.toInt())
            val i = Intent(this, SelectedCourseScreen::class.java)
            i.putExtra("key",past_intent)
            startActivity(i)
        }
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun get(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getInfoCourse(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    val info = Gson().fromJson<CourseInfo>(prettyJson)

                    Glide.with(applicationContext).load("http://mitiusxa.beget.tech${info.img}").into(fullImg)
                    courseAuthor.text = info.author
                    courseOpisanie.text = info.discript
                    courseName.text = info.name

                } else if (response.code() == 401){
                    val refresh_response = service.refreshToken()




                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }

        }
    }

    fun post(id: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.pushPostSub(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()?.string()
                        )
                    )



                    Log.d("Pretty Printed JSON :", "$prettyJson")
                } else {
                    Toast.makeText(applicationContext, "Неизвестная ошибка", Toast.LENGTH_SHORT).show()
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }


    }

    fun get2(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSubCourse(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val str = response.body()?.string()
                    if (str == "\"1\"") {
                        courseBtn.text = "Продолжить"
                    }

                    Log.d("Pretty Printed JSON :", "$str")
                } else {
                    Toast.makeText(applicationContext, "Неизвестная ошибка", Toast.LENGTH_SHORT).show()
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    Log.d("MAIN", "${response.body()?.toString()}")
                }
            }
        }
    }

}