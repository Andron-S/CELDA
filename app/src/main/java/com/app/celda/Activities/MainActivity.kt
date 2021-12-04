package com.app.celda.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.celda.Adapter.CourseAdapter
import com.app.celda.Json.ImageJSONReader
import com.app.celda.R
import com.app.celda.database.DBCelda
import kotlinx.android.synthetic.main.main_screen.*

class MainActivity : AppCompatActivity(), CourseAdapter.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        val db = DBCelda(this)
        db.openDB()
        initialization()
    }

    private fun initialization() {
        val adapter = CourseAdapter(this)
        rcCourses.adapter = adapter
        rcCourses.layoutManager = LinearLayoutManager(this)

        for (bitmap in ImageJSONReader().getList()) {
            adapter.addItem(bitmap)
            Log.i("KARTINKA: ", "$bitmap")
        }
    }

    override fun onItemClick() {
        startActivity(Intent(baseContext, SelectedCourseScreen::class.java))
    }
}

// Показать видос "ИСПАНЦЫ"