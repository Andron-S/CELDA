package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.app.celda.R
import com.app.celda.User

class LoginActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin : Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            getFromEditText()
        }
    }

    private fun getFromEditText()
    {
        val user = User()
        val emailEditText : EditText = findViewById(R.id.editTextEmail)
        user.Email = emailEditText.text.toString()

        val pwdEditText : EditText = findViewById(R.id.editTextPassword)
        user.Pwd = pwdEditText.text.toString()
        //Сделай проверку логина и пароля из api или бд
    }

    fun onClickIntentReg(view: android.view.View) {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
    }
}