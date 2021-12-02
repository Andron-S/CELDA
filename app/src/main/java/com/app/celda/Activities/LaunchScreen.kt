package com.app.celda

import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

class LaunchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        val mCpuImageView: ImageView = findViewById<View>(R.id.logo) as ImageView
        val drawable: Drawable = mCpuImageView.getDrawable()
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 3900)
    }

}