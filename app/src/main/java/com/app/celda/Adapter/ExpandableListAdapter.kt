package com.app.celda.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.app.celda.R

class ExpandableListAdapter() : BaseExpandableListAdapter() {

    private lateinit var _context : Context
    private lateinit var _listDataHeader : List<String>
    private lateinit var _listDataChild : HashMap<String, List<String>>
    private lateinit var anime : Animation
    private lateinit var trueOrFalseAnime : HashMap<Long, Boolean>

    constructor(context : Context, listDataHeader : List<String>, listChildData : HashMap<String, List<String>>) : this() {
        _context = context
        _listDataChild = listChildData
        _listDataHeader = listDataHeader
    }

    override fun getGroupCount(): Int {
        return _listDataHeader.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return _listDataChild[_listDataHeader[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return _listDataHeader[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return _listDataChild[_listDataHeader[p0]]!![p1]
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
        val headerTittle : String = getGroup(groupPosition).toString()
        val v : View? = LayoutInflater.from(parent?.context).inflate(R.layout.module_group, null)

//
//        for (i in _listDataChild[_listDataHeader[groupPosition]]!![0].length until _listDataChild[_listDataHeader[groupPosition]]!![0].length) {
//            trueOrFalseAnime.put(getChildId(groupPosition, i), false)
//        }

        val moduleTittle : TextView = v!!.findViewById(R.id.moduleTittle)
        moduleTittle.text = headerTittle
        return v
    }

    @SuppressLint("InflateParams")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val childText : String = getChild(groupPosition,childPosition).toString()
        val v : View? = LayoutInflater.from(parent?.context).inflate(R.layout.item_module_lesson, null)

//        if (trueOrFalseAnime.containsKey(getChildId(groupPosition,childPosition))) {
//            if (trueOrFalseAnime[getChildId(groupPosition,childPosition)] == false) {
//                anime = AnimationUtils.loadAnimation(parent?.context, R.anim.test)
//                v?.startAnimation(anime)
//            }
//        }
        anime = AnimationUtils.loadAnimation(parent?.context, R.anim.test)
        v?.startAnimation(anime)

        val lessonTitle : TextView = v!!.findViewById(R.id.lessonTitle)
        lessonTitle.text = childText
        return v
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}