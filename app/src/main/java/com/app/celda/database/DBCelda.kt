package com.app.celda.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DBCelda(var context : Context) {
    var database : SQLiteDatabase? = null
    var dbHelper = DBHelper(context)

    public fun openDB()
    {
        database = dbHelper.writableDatabase
    }
}