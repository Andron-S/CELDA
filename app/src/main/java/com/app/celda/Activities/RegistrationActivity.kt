package com.app.celda.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.celda.R
import com.app.celda.User

class RegistrationActivity : AppCompatActivity() {
    companion object {
        val user = User()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val btnReg : Button = findViewById(R.id.btnReg)
        btnReg.setOnClickListener {
            if (getFromEditText()) {
                //Запись в бд
            }
        }
    }
    private fun getFromEditText() : Boolean
    {
        val loginEditText : EditText = findViewById(R.id.editTextLoginRes)
        user.Login = loginEditText.text.toString()

        val emailEditText : EditText = findViewById(R.id.editTextEmailReg)
        user.Email = emailEditText.text.toString()

        val pwdEditText : EditText = findViewById(R.id.editTextPwdReg)
        user.Pwd = pwdEditText.text.toString()

        val repPwdEditText : EditText = findViewById(R.id.editTextRepeatPwdReg)
        val repPwd = repPwdEditText.text.toString()

        if (user.Pwd != repPwd) {
            Toast.makeText(this, "Пароль не сопадает", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun onClickIntentLogIn(view: android.view.View) {
        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}