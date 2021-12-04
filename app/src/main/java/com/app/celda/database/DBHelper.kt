package com.app.celda.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context : Context) : SQLiteOpenHelper(context, "DBCelda.db", null, 1) {
    private val TABLE_COURSE_NAME = "courseName"
    private val ID_COURSE = "id_course"
    private val COURSE_AUTHOR = "author"
    private val COURSE_NAME = "courseName"
    private val COURSE_DESCRIPTION = "CourseDescription"
    private val IMG_PREVIEW = "imgPreview"

    private val TABLE_MODULE_NAME = "moduleName"
    private val ID_MODULE = "id_module"
    private val MODULE_NAME = "moduleName"

    private val TABLE_LESSON = "lesson"
    private val ID_LESSON = "id_lesson"
    private val LESSON_NAME = "lessonName"
    private val LESSON_DESCRIPTION = "lessonDescription"
    private val LESSON = "lesson"
    private val VIDEO = "video"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys=on; ")
        db?.execSQL("CREATE TABLE ${TABLE_COURSE_NAME} (" +
                "${ID_COURSE} INTEGER PRIMARY KEY," +
                "${ID_MODULE} INTEGER," +
                "${COURSE_AUTHOR} TEXT," +
                "${COURSE_NAME} TEXT," +
                "${COURSE_DESCRIPTION} TEXT," +
                "${IMG_PREVIEW} TEXT," +
                "FOREIGN KEY (${ID_MODULE}) REFERENCES ${TABLE_MODULE_NAME}(${ID_MODULE})" +
                ")")
        db?.execSQL("CREATE TABLE ${TABLE_MODULE_NAME} (" +
                "${ID_MODULE} INTEGER PRIMARY KEY," +
                "${ID_LESSON} INTEGER," +
                "${MODULE_NAME} TEXT," +
                "FOREIGN KEY (${ID_LESSON}) REFERENCES ${TABLE_LESSON}(${ID_LESSON})" + ")")
        db?.execSQL("CREATE TABLE ${TABLE_LESSON} (" +
                "${ID_LESSON} INTEGER PRIMARY KEY," +
                "${LESSON_NAME} TEXT," +
                "${LESSON_DESCRIPTION} TEXT," +
                "${LESSON} TEXT," +
                "${VIDEO} TEXT " + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}