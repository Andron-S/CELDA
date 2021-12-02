package com.app.celda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.celda.Adapter.CourseAdapter
import com.app.celda.Json.ImageJSONReader
import kotlinx.android.synthetic.main.main_screen.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        initialization()
    }

    private fun initialization() {
        val adapter = CourseAdapter()
        rcCourses.adapter = adapter
        rcCourses.layoutManager = LinearLayoutManager(this)

        for (bitmap in ImageJSONReader().getList()) {
            adapter.addItem(bitmap)
            Log.i("KARTINKA: ", "$bitmap")
        }
    }
}

// Показать видос "ИСПАНЦЫ"