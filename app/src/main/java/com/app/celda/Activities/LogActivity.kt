package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.celda.Post.APIService
import com.app.celda.Post.TokenObj
import com.app.celda.R
import com.app.celda.TokenManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class LogActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textReg.setOnClickListener{
            val i = Intent(this@LogActivity, RegActivity::class.java)
            startActivity(i)
        }


        btnLogin.setOnClickListener {
            if (editTextUserName.text.toString().trim() == "" || editTextPassword.text.toString().trim() == "") {
                Toast.makeText(applicationContext, "Empty fields detected", Toast.LENGTH_SHORT).show()
            } else {
                post()
            }

        }
    }

    fun post() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("username", editTextUserName.text)
        jsonObject.put("password", editTextPassword.text)
        // Log.d("TAGG:::  ","${jsonObject}")

        val jsonObjectString = jsonObject.toString()
        val rb = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.pushPostLog(rb)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()?.string()
                        )
                    )
                    val jsonToken = JSONObject(prettyJson)
                    TokenManager().setToken(jsonToken.get("access").toString())
                    TokenManager().setRefresh(jsonToken.get("refresh").toString())

                    val i = Intent(this@LogActivity, MainActivity::class.java)
                    startActivity(i)

                    Log.e("RETROFIT_ERROR", response.code().toString())
                    Log.d("MAIN", "${response.body()?.toString()}")
                    Log.d("Pretty Printed JSON :", "$prettyJson")
                } else if (response.code() == 401) {
                    Toast.makeText(applicationContext, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    Log.d("MAIN", "${response.body()?.toString()}")
                } else {
                    Toast.makeText(applicationContext, "Неизвестная ошибка", Toast.LENGTH_SHORT).show()
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    Log.d("MAIN", "${response.body()?.toString()}")
                }
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}