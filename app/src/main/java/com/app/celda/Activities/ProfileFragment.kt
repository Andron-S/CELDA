package com.app.celda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.celda.Model.CourseProfileModel
import com.app.celda.Model.ProfileModel
import com.app.celda.Post.APIService
import com.app.celda.R
import com.app.celda.TokenManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.profile_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit

class ProfileFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        get()

        btnExit.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onClick(p0: View?) {
                TokenManager().clearTokens()
                val i = Intent(requireContext(), LogActivity::class.java)
                startActivity(i)
            }

        })


    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun get() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()


        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getProfile()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {


                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    val profile = Gson().fromJson<ProfileModel>(JSONObject(prettyJson).get("profile").toString())
                    profile.name = Gson().fromJson<String>(JSONObject(prettyJson).get("username").toString())
                    val author_course = Gson().fromJson<List<CourseProfileModel>>(JSONObject(prettyJson).get("author_courses").toString())
                    val courses_done = Gson().fromJson<List<CourseProfileModel>>(JSONObject(prettyJson).get("taking_courses").toString())

                    Log.d("Pretty Printed JSON :", prettyJson)

                    tvLogin.text = profile.name
                    tvAboutMe.text = profile.about
                    var str = ""
                    if (!author_course.isEmpty()) {
                        for (i in 0 until author_course.size) {
                            str += "${author_course[i].name_course}, "
                            if (i == author_course.size) {
                                str += "${author_course[i].name_course}."
                            }
                        }
                    }
                    tvCreator.text = str

                    str = ""
                    if (!courses_done.isEmpty()) {
                        for (i in 0 until courses_done.size) {
                            str += "${courses_done[i].name_course}, "
                            if (i == courses_done.size) {
                                str += "${courses_done[i].name_course}."
                            }
                        }
                    }
                    tvPassedCourses.text = str


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }


}