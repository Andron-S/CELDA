package com.app.celda.Activities

import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.app.celda.R

class LaunchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)
        val mCpuImageView: ImageView = findViewById<View>(R.id.logo) as ImageView
        val drawable: Drawable = mCpuImageView.drawable
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }

        Handler().postDelayed({
            // Если в аккаунт уже зайдено, то надо подавать интент на главную страницу
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }, 1000) // 3900
    }

}