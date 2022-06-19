package com.app.celda.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.celda.Model.LessonDoneModel
import com.app.celda.Model.LessonModel
import com.app.celda.Model.ModuleDoneModel
import com.app.celda.Model.ModulesModel
import com.app.celda.R
import kotlinx.coroutines.NonDisposableHandle.parent

class ExpandableListAdapter() : BaseExpandableListAdapter() {

    private lateinit var _context : Context
    private lateinit var _listDataHeader : List<ModulesModel>
    private lateinit var _listDataChild : List<List<LessonModel>>
    private lateinit var _listDoneModule : List<ModuleDoneModel>
    private lateinit var _listDoneLesson : List<LessonDoneModel>

    private lateinit var anime : Animation

    constructor(context : Context, listDataHeader : List<ModulesModel>, listChildData : List<List<LessonModel>>, listDoneModule : List<ModuleDoneModel>, listDoneLesson : List<LessonDoneModel>) : this() {
        _context = context
        _listDataChild = listChildData
        _listDataHeader = listDataHeader
        _listDoneModule = listDoneModule
        _listDoneLesson = listDoneLesson
    }

    override fun getGroupCount(): Int {
        return _listDataHeader.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return _listDataChild[p0].size
    }

    override fun getGroup(p0: Int): Any {

        return _listDataHeader[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {

        return _listDataChild[p0][p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val headerTittle : String = (getGroup(groupPosition) as ModulesModel).module_name
        val v : View? = LayoutInflater.from(parent?.context).inflate(R.layout.module_group, null)
        Log.d("GDE NAZVANIYA:", "$headerTittle | ")
        val img : ImageView = v?.findViewById(R.id.moduleDone)!!
        if ((_listDoneModule[groupPosition].is_module_done).toBoolean()) {
            img.setImageResource(R.drawable.done_module)
        }
        val moduleTittle : TextView = v!!.findViewById(R.id.moduleTittle)
        moduleTittle.text = headerTittle
        return v
    }

    @SuppressLint("InflateParams")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val childTitle : String = (getChild(groupPosition,childPosition) as LessonModel).name
        val childDiscript : String = (getChild(groupPosition,childPosition) as LessonModel).discript
        Log.d("GDE NAZVANIYA:", "$childTitle | $childDiscript")
        val v : View? = LayoutInflater.from(parent?.context).inflate(R.layout.item_module_lesson, null)
        val img : ImageView = v?.findViewById(R.id.imgDoneLesson)!!
        if ((_listDoneLesson[childPosition].is_lesson_done).toBoolean()) {
            img.visibility = View.VISIBLE
        }

        //anime = AnimationUtils.loadAnimation(parent?.context, R.anim.test)
        //v?.startAnimation(anime)

        val lessonTitle : TextView = v!!.findViewById(R.id.lessonTitle)
        lessonTitle.text = childTitle

        val lessonDiscript : TextView = v!!.findViewById(R.id.lessonDescription)
        lessonDiscript.text = childDiscript
        return v
    }


    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}