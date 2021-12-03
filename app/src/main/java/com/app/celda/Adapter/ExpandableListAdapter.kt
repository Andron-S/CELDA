package com.app.celda.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.app.celda.R

class ExpandableListAdapter() : BaseExpandableListAdapter() {

    private lateinit var _context : Context
    private lateinit var _listDataHeader : List<String>
    private lateinit var _listDataChild : HashMap<String, List<String>>

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

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val headerTittle : String = getGroup(p0).toString()
        val v : View? = LayoutInflater.from(p3?.context).inflate(R.layout.module_group, null)

        val moduleTittle : TextView = v!!.findViewById(R.id.moduleTittle)
        moduleTittle.text = headerTittle
        return v
    }

    @SuppressLint("InflateParams")
    override fun getChildView(p0: Int, p1: Int, p2: Boolean, convertView: View?, p4: ViewGroup?): View {
        val childText : String = getChild(p0,p1).toString()
        val v : View? = LayoutInflater.from(p4?.context).inflate(R.layout.item_module_lesson, null)

        val lessonTitle : TextView = v!!.findViewById(R.id.lessonTitle)
        lessonTitle.text = childText
        return v
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}