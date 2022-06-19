package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.celda.Post.APIService
import com.app.celda.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit


class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnHaveAccount.setOnClickListener{
            val i = Intent(this@RegActivity, LogActivity::class.java)
            startActivity(i)
        }


        btnReg.setOnClickListener{
            if (editTextLoginReg.text.toString().trim() == "" ||
                editTextEmailReg.text.toString().trim() == "" ||
                editTextPwdReg.text.toString().trim() == "" ||
                editTextRepeatPwdReg.text.toString().trim() == "") {
                Toast.makeText(applicationContext, "Empty fields detected", Toast.LENGTH_SHORT).show()
            } else {
                if (editTextPwdReg.text.toString().trim() == editTextRepeatPwdReg.text.toString().trim()) {
                    post()
                } else {
                    Toast.makeText(applicationContext, "Passwords don't match", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    fun post() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mitiusxa.beget.tech")
            .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("username", editTextLoginReg.text)
        jsonObject.put("email", editTextEmailReg.text)
        jsonObject.put("password", editTextPwdReg.text)
        jsonObject.put("password2", editTextRepeatPwdReg.text)

        val jsonObjectString = jsonObject.toString()
        val rb = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.pushPostReg(rb)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()?.string()
                        )
                    )
                    val jsonObj = JSONObject(prettyJson)
                        if (jsonObj.length() == 1) {
                            Toast.makeText(applicationContext, "${jsonObj.get("message")}", Toast.LENGTH_SHORT).show()
                        } else {
                            val i = Intent(this@RegActivity, MainActivity::class.java)
                            startActivity(i)
                        }

                    Log.e("RETROFIT_ERROR", response.code().toString())
                    Log.d("MAIN", "${response.body()?.toString()}")
                    Log.d("Pretty Printed JSON :", "${jsonObj.get("message")}")
                    Log.d("SIZE JSON::", "${jsonObj.length()}")
                } else if (response.code() == 400) {
                    Toast.makeText(applicationContext, "Пользователь уже создан", Toast.LENGTH_SHORT).show()
                    Log.d("MAIN", "${response.body()?.toString()}")
                    Log.e("RETROFIT_ERROR", response.code().toString())
                } else {

                    Log.d("MAIN", "${response.body()?.toString()}")
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}